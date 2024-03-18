package ru.nikitos.topfive.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nikitos.topfive.rest.data.TopRepository;
import ru.nikitos.topfive.rest.entity.Top;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DefaultTopService implements TopService {

    @Autowired
    private TopRepository topRepository;

    @Override
    public List<Top> getAllTops() {
        return topRepository.findAll();
    }

    @Override
    public Top createTop(String title, String details) {
        return topRepository.save(new Top(null, title, details));
    }

    @Override
    public Optional<Top> getTop(Long topId) {
        return topRepository.findById(topId);
    }

    @Override
    public Top updateTop(Long topId, String title, String details) {
        Top top = topRepository.findById(topId).orElseThrow(NoSuchElementException::new);
        top.setTitle(title);
        top.setDetails(details);
        return topRepository.save(top);
    }

    @Override
    public void deleteTop(Long topId) {
        topRepository.deleteById(topId);
    }
}