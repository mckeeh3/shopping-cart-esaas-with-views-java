package io.shopping.cart.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.akkaserverless.javasdk.view.ViewContext;
import com.google.protobuf.Any;
import com.google.protobuf.Timestamp;

import io.shopping.cart.entity.PurchasedProductEntity;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class PurchasedProductsView extends AbstractPurchasedProductsView {
  private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

  public PurchasedProductsView(ViewContext context) {
  }

  @Override
  public PurchasedProductsOuter.PurchasedProduct emptyState() {
    return PurchasedProductsOuter.PurchasedProduct.getDefaultInstance();
  }

  @Override
  public UpdateEffect<PurchasedProductsOuter.PurchasedProduct> processItemCheckedOut(PurchasedProductsOuter.PurchasedProduct state, PurchasedProductEntity.PurchasedProductState command) {
    return effects().updateState(
        state.toBuilder()
            .setCustomerId(command.getCustomerId())
            .setCartId(command.getCartId())
            .setProductId(command.getProductId())
            .setProductName(command.getProductName())
            .setQuantity(command.getQuantity())
            .setPurchasedUtc(toUtc(command.getPurchasedUtc()))
            .build());
  }

  @Override
  public UpdateEffect<PurchasedProductsOuter.PurchasedProduct> ignoreOtherEvents(PurchasedProductsOuter.PurchasedProduct state, Any any) {
    return effects().ignore();
  }

  static String toUtc(Timestamp timestamp) {
    return dateFormat.format(new Date(timestamp.getSeconds() * 1000 + timestamp.getNanos() / 1000000));
  }
}
