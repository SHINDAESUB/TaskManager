package com.taskManager.web.api;

import com.taskManager.domain.application.service.CardListService;
import com.taskManager.domain.common.security.CurrentUser;
import com.taskManager.domain.model.cardlist.CardList;
import com.taskManager.domain.model.user.AuthenticatedUser;
import com.taskManager.web.payload.AddCardListPayload;
import com.taskManager.web.payload.ChangeCardListPositionsPayload;
import com.taskManager.web.result.AddCardListResult;
import com.taskManager.web.result.ApiResult;
import com.taskManager.web.result.Result;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CardListApiController {

  private CardListService cardListService;

  public CardListApiController(CardListService cardListService) {
    this.cardListService = cardListService;
  }

  @PostMapping("/api/card-lists")
  public ResponseEntity<ApiResult> addCardList(@RequestBody AddCardListPayload payload,
                                               @CurrentUser AuthenticatedUser currentUser) {
    CardList cardList = cardListService.addCardList(payload.toCommand(currentUser.getUserId()));
    return AddCardListResult.build(cardList);
  }

  @PostMapping("/api/card-lists/positions")
  public ResponseEntity<ApiResult> changeCardListPositions(@RequestBody ChangeCardListPositionsPayload payload) {
    cardListService.changePositions(payload.toCommand());
    return Result.ok();
  }
}