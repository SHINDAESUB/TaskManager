package com.taskManager.web.api;

import com.taskManager.domain.application.service.CardService;
import com.taskManager.domain.common.security.CurrentUser;
import com.taskManager.domain.model.card.Card;
import com.taskManager.domain.model.user.AuthenticatedUser;
import com.taskManager.web.payload.AddCardPayload;
import com.taskManager.web.payload.ChangeCardPositionsPayload;
import com.taskManager.web.result.AddCardResult;
import com.taskManager.web.result.ApiResult;
import com.taskManager.web.result.Result;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CardApiController {

  private CardService cardService;

  public CardApiController(CardService cardService) {
    this.cardService = cardService;
  }

  @PostMapping("/api/cards")
  public ResponseEntity<ApiResult> addCard(@RequestBody AddCardPayload payload,
                                           @CurrentUser AuthenticatedUser currentUser) {
    Card card = cardService.addCard(payload.toCommand(currentUser.getUserId()));
    return AddCardResult.build(card);
  }

  @PostMapping("/api/cards/positions")
  public ResponseEntity<ApiResult> changeCardPositions(@RequestBody ChangeCardPositionsPayload payload) {
    cardService.changePositions(payload.toCommand());
    return Result.ok();
  }
}