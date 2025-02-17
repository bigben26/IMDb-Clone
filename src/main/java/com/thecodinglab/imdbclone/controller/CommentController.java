package com.thecodinglab.imdbclone.controller;

import com.thecodinglab.imdbclone.payload.*;
import com.thecodinglab.imdbclone.security.CurrentUser;
import com.thecodinglab.imdbclone.security.UserPrincipal;
import com.thecodinglab.imdbclone.service.CommentService;
import com.thecodinglab.imdbclone.validation.Pagination;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

  private final CommentService commentService;

  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @GetMapping("/{commentId}")
  public ResponseEntity<CommentRecord> getCommentById(@PathVariable Long commentId) {
    return new ResponseEntity<>(commentService.getComment(commentId), HttpStatus.OK);
  }

  @GetMapping("/{movieId}/comments")
  public ResponseEntity<PagedResponse<CommentRecord>> getCommentsByMovieId(
      @PathVariable Long movieId,
      @RequestParam(required = false, defaultValue = Pagination.DEFAULT_PAGE_NUMBER) Integer page,
      @RequestParam(required = false, defaultValue = Pagination.DEFAULT_PAGE_SIZE) Integer size) {
    return new ResponseEntity<>(
        commentService.getCommentsByMovieId(movieId, page, size), HttpStatus.OK);
  }

  @PostMapping("/{movieId}")
  public ResponseEntity<CommentRecord> createComment(
      @PathVariable Long movieId,
      @Valid @RequestBody CreateCommentRequest request,
      @CurrentUser UserPrincipal currentAccount) {
    return new ResponseEntity<>(
        commentService.createComment(movieId, request, currentAccount), HttpStatus.CREATED);
  }

  @PutMapping("/{commentId}")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public ResponseEntity<CommentRecord> updateComment(
      @PathVariable Long commentId,
      @Valid @RequestBody UpdateCommentRequest request,
      @CurrentUser UserPrincipal currentAccount) {
    return new ResponseEntity<>(
        commentService.updateComment(commentId, request, currentAccount), HttpStatus.OK);
  }

  @DeleteMapping("/{commentId}")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public ResponseEntity<MessageResponse> deleteComment(
      @PathVariable Long commentId, @CurrentUser UserPrincipal currentAccount) {
    return new ResponseEntity<>(
        commentService.deleteComment(commentId, currentAccount), HttpStatus.OK);
  }
}
