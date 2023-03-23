package com.epam.esm.controller;

import com.epam.esm.TagService;
import com.epam.esm.dto.TagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/tags")
public class TagController {

    private final TagService tagService;

    @GetMapping(value = "/{id}")
    public TagDto getTag(
            @PathVariable("id") Long id) {
        return tagService.getBy(id);
    }

    @GetMapping
    @ResponseBody
    public List<TagDto> getAllTag() {
        return tagService.getAll();
    }
}
