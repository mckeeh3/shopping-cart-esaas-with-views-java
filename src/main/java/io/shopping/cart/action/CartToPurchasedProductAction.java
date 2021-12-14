package io.shopping.cart.action;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.shopping.cart.api.PurchasedProductApi;
import io.shopping.cart.entity.CartEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class CartToPurchasedProductAction extends AbstractCartToPurchasedProductAction {
  private static final Logger log = LoggerFactory.getLogger(CartToPurchasedProductAction.class);

  public CartToPurchasedProductAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onCartCheckedOut(CartEntity.CartCheckedOut cartCheckedOut) {
    log.info("{}", cartCheckedOut);

    var results = cartCheckedOut.getCartState().getLineItemsList().stream()
        .map(lineItem -> PurchasedProductApi.PurchasedProduct.newBuilder()
            .setCustomerId(cartCheckedOut.getCartState().getCustomerId())
            .setCartId(cartCheckedOut.getCartState().getCartId())
            .setProductId(lineItem.getProductId())
            .setProductName(lineItem.getProductName())
            .setQuantity(lineItem.getQuantity())
            .setPurchasedUtc(cartCheckedOut.getCartState().getCheckedOutUtc())
            .build())
        .map(purchasedProduct -> components().purchasedProduct().addPurchasedProduct(purchasedProduct).execute())
        .collect(Collectors.toList());

    var result = CompletableFuture.completedFuture(effects().reply(Empty.getDefaultInstance()));
    CompletableFuture.allOf(results.toArray(new CompletableFuture[results.size()])).whenComplete((v, e) -> {
      if (e != null) {
        throw new RuntimeException(e);
      }
    }).join();

    return effects().asyncEffect(result);
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }
}
