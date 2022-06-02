package com.example.quangca.controllers.ui;

import com.example.quangca.dto.ChapterDto;
import com.example.quangca.dto.GenreDto;
import com.example.quangca.dto.NovelDto;
import com.example.quangca.dto.UserDto;
import com.example.quangca.models.Novel;
import com.example.quangca.request.ChangePasswordForm;
import com.example.quangca.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
//@RequiredArgsConstructor
//@AllArgsConstructor
public class UIController {
    private final NovelService novelService;


    private final ChapterService chapterService;

    private final GenreService genreService;

    private final UserService userService;

    private final NovelFavoriteService novelFavoriteService;

    @Autowired
    public UIController(NovelService novelService, ChapterService chapterService, GenreService genreService, UserService userService, NovelFavoriteService novelFavoriteService) {
        this.novelService = novelService;
        this.chapterService = chapterService;
        this.genreService = genreService;
        this.userService = userService;
        this.novelFavoriteService = novelFavoriteService;
    }

    @GetMapping("/index")
    public String getNovels(Model model, @RequestParam(value = "limit", required = false) Integer limit) {
        List<NovelDto> novels = novelService.getNovels(limit);
        model.addAttribute("novels", novels);
        return "index";
    }

    @GetMapping("/novel/{novel_id}")
    public String getNovel(Model model, @PathVariable("novel_id") int novelId) {
        NovelDto novel = novelService.getNovel(novelId);
        List<ChapterDto> chapters = chapterService.getChapters(novelId);
        model.addAttribute("novel", novel);
        model.addAttribute("chapters", chapters);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDto currentUser = userService.findUserByUsername(((UserDetails) principal).getUsername());
            for (Novel temp : currentUser.getFavoritedNovels()) {
                if (temp.getId() == novel.getId()) {
                    model.addAttribute("favorited", Boolean.TRUE);
                    return "novel";
                }
            }

            model.addAttribute("favorited", Boolean.FALSE);

        } else {
            model.addAttribute("favorited", Boolean.FALSE);
        }
        return "novel";
    }

    @PutMapping("/novel/{novel_id}/favorite")
    public String addNovelFavorite(Model model, @PathVariable("novel_id") int novelId) {
        NovelDto novel = novelService.getNovel(novelId);
        List<ChapterDto> chapters = chapterService.getChapters(novelId);
        model.addAttribute("novel", novel);
        model.addAttribute("chapters", chapters);
        if (userService.isUserLoggedIn()) {
            model.addAttribute("favorited", userService.favoriteUnfavoriteNovel(novelId) ? Boolean.TRUE : Boolean.FALSE);
        } else {
            model.addAttribute("favorited", Boolean.FALSE);
        }
        return "novel";
    }

    @GetMapping("/c{chapter_id}")
    public String getChapter(Model model, @PathVariable("chapter_id") int chapterId) {
        ChapterDto currentChapter = chapterService.getChapter(chapterId);
        NovelDto novel = novelService.getNovel(currentChapter.getNovelId());
        List<ChapterDto> chapters = chapterService.getChapters(currentChapter.getNovelId());
        //chapter.setContent(chapter.getContent().replaceAll("(\r\n|\n)", "<br />"));
        chapterService.sortChapter(chapters);
        List<ChapterDto> adjacentChapters = chapterService.getAdjacentChapters(currentChapter, chapters);
        model.addAttribute("novel", novel);
        model.addAttribute("chapter", currentChapter);
        model.addAttribute("chapters", chapters);
        model.addAttribute("adjacentChapters", adjacentChapters);
        return "chapter";
    }

    @GetMapping("/genre/{genre_id}")
    public String getNovelsByGenre(Model model, @PathVariable("genre_id") int genreId) {
        GenreDto genre = genreService.getGenre(genreId);
        List<NovelDto> novels = novelService.getNovelsByGenre(genreId);
        model.addAttribute("genre", genre);
        model.addAttribute("novels", novels);
        return "genre";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logoutSuccessful")
    public String logoutSuccessful() {
        return "logoutSuccessful";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new UserDto());
        model.addAttribute("error", Boolean.FALSE);
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(Model model, @Valid @ModelAttribute UserDto user) {
        UserDto newUser = userService.signupMember(user);
        if (newUser == null) {
            model.addAttribute("user", new UserDto());
            model.addAttribute("error", Boolean.TRUE);
            return "signup";
        }
        model.addAttribute("user", newUser);
        return "signupSuccessful";
    }

    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public String getUser(Model model) {
        UserDto currentUser = userService.getCurrentLoggedInUserDto();
        model.addAttribute("user", currentUser);
        return "user";
    }

    @GetMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public String changePassword(Model model) {
        model.addAttribute("changePasswordForm", new ChangePasswordForm("",""));
        model.addAttribute("wrongpassword", false);
        return "changePassword";
    }

    @PostMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public String changePasswordHandle(Model model, @ModelAttribute ChangePasswordForm changePasswordForm) {
        if (userService.changePassword(changePasswordForm.newPassword(), changePasswordForm.oldPassword())) {
            return "changePasswordSuccessful";
        }
        model.addAttribute("changePasswordForm", new ChangePasswordForm("",""));
        model.addAttribute("wrongpassword", true);
        return "changePassword";
    }

    @GetMapping("/access-denied")
    public String getAccessDenied() {
        return "accessDenied";
    }

}
