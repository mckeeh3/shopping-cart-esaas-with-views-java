package io.mystore.order.action;

import java.util.List;
import java.util.stream.Collectors;

import com.akkaserverless.javasdk.action.ActionCreationContext;
import com.google.protobuf.Any;
import com.google.protobuf.Empty;

import io.mystore.cart.entity.CartEntity;
import io.mystore.cart.entity.CartEntity.CartState;
import io.mystore.order.api.OrderApi;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class CartToOrderAction extends AbstractCartToOrderAction {

  public CartToOrderAction(ActionCreationContext creationContext) {
  }

  @Override
  public Effect<Empty> onCartCheckedOut(CartEntity.CartCheckedOut command) {
    var getCartState = components().order().createOrder(toCreateOrderCommand(command.getCartState()));

    return effects().forward(getCartState);
  }

  private OrderApi.CreateOrderCommand toCreateOrderCommand(CartState state) {
    return OrderApi.CreateOrderCommand
        .newBuilder()
        .setOrderId(state.getCartId())
        .setCustomerId(state.getCustomerId())
        .setOrderedUtc(state.getCheckedOutUtc())
        .addAllOrderItems(toOrderItems(state.getLineItemsList()))
        .build();
  }

  private List<OrderApi.OrderItem> toOrderItems(List<CartEntity.LineItem> lineItems) {
    return lineItems.stream().map(
        lineItem -> OrderApi.OrderItem
            .newBuilder()
            .setSkuId(lineItem.getSkuId())
            .setSkuName(lineItem.getSkuName())
            .setQuantity(lineItem.getQuantity())
            .build())
        .collect(Collectors.toList());
  }

  @Override
  public Effect<Empty> ignoreOtherEvents(Any any) {
    return effects().reply(Empty.getDefaultInstance());
  }
}
