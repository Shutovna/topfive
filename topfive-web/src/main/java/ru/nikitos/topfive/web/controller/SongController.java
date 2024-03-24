package ru.nikitos.topfive.web.controller;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.ArrayUtils;
import ru.nikitos.topfive.entities.Song;
import ru.nikitos.topfive.entities.Top;
import ru.nikitos.topfive.entities.payload.NewSongPayload;
import ru.nikitos.topfive.web.client.BadRequestException;
import ru.nikitos.topfive.web.client.SongRestClient;

import java.io.IOException;
import java.util.Arrays;

@Controller
@RequestMapping("/items/song")
@Slf4j
public class SongController {

    @Autowired
    SongRestClient songRestClient;

    @ModelAttribute("topId")
    public Integer topId(@RequestParam Integer topId) {
        System.out.println("ModelAttribute topId = " + topId);
        return topId;
    }


    @GetMapping("create")
    public String showCreateForm(Model model, HttpServletRequest request) {
        System.out.println("top = " + request.getSession().getAttribute("top"));
        model.addAttribute("song", new Song());
        return "items/new_song";
    }

    @PostMapping("create")
    public String createSong(@RequestParam("file") MultipartFile file, NewSongPayload songPayload,
                             @ModelAttribute("topId") Integer topId, Model model) {
        try {
            String originalFilename = file.getOriginalFilename();
            byte[] data = file.getBytes();
            if (!StringUtils.isEmpty(originalFilename) && data.length != 0 ) {
                songPayload.setFileName(originalFilename);
                songPayload.setData(data);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            log.debug("Creating " + songPayload);
            Song song = songRestClient.createSong(songPayload);
            return "redirect:/tops/%d".formatted(topId);
        } catch (BadRequestException e) {
            log.warn("Errors in creating song {}", e.getErrors());
            model.addAttribute("errors", e.getErrors());
            model.addAttribute("song", songPayload);
            return "items/new_song";
        }
    }
}
