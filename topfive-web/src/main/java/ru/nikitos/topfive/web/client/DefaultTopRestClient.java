package ru.nikitos.topfive.web.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.nikitos.topfive.entities.Top;
import ru.nikitos.topfive.entities.TopType;
import ru.nikitos.topfive.entities.payload.NewTopPayload;
import ru.nikitos.topfive.entities.payload.UpdateTopPayload;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class DefaultTopRestClient implements TopRestClient {

    private static final ParameterizedTypeReference<List<Top>> TOPS_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {
            };

    private final RestClient restClient;

    @Override
    public List<Top> findAllTops(String filter) {
        return this.restClient
                .get()
                .uri("/topfive-api/tops?filter={filter}", filter)
                .retrieve()
                .body(TOPS_TYPE_REFERENCE);
    }

    @Override
    public Top createTop(NewTopPayload newTopPayload) {
        try {
            return this.restClient
                    .post()
                    .uri("/topfive-api/tops")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(newTopPayload)
                    .retrieve()
                    .body(Top.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    public Optional<Top> findTop(Long topId) {
        try {
            return Optional.ofNullable(this.restClient.get()
                    .uri("/topfive-api/tops/{topId}", topId)
                    .retrieve()
                    .body(Top.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    @Override
    public void updateTop(Long topId, UpdateTopPayload updateTopPayload) {
        try {
            this.restClient
                    .patch()
                    .uri("/topfive-api/tops/{topId}", topId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(updateTopPayload)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public void deleteTop(Long topId) {
        try {
            this.restClient
                    .delete()
                    .uri("/topfive-api/tops/{topId}", topId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
