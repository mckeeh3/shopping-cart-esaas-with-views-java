package io.mystore.shipping.entity;

import com.akkaserverless.javasdk.testkit.ValueEntityResult;
import com.akkaserverless.javasdk.valueentity.ValueEntity;
import com.google.protobuf.Empty;
import io.mystore.shipping.api.ShipOrderItemApi;
import org.junit.Test;

import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Akka Serverless tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShipOrderItemTest {

  @Test
  public void exampleTest() {
    ShipOrderItemTestKit testKit = ShipOrderItemTestKit.of(ShipOrderItem::new);
    // use the testkit to execute a command
    // of events emitted, or a final updated state:
    // ValueEntityResult<SomeResponse> result = testKit.someOperation(SomeRequest);
    // verify the response
    // SomeResponse actualResponse = result.getReply();
    // assertEquals(expectedResponse, actualResponse);
    // verify the final state after the command
    // assertEquals(expectedState, testKit.getState());
  }

  @Test
  public void addShipOrderItemTest() {
    ShipOrderItemTestKit testKit = ShipOrderItemTestKit.of(ShipOrderItem::new);
    // ValueEntityResult<Empty> result = testKit.addShipOrderItem(ShipOrderItem.newBuilder()...build());
  }


  @Test
  public void getShipOrderItemTest() {
    ShipOrderItemTestKit testKit = ShipOrderItemTestKit.of(ShipOrderItem::new);
    // ValueEntityResult<ShipOrderItem> result = testKit.getShipOrderItem(GetShipOrderItemRequest.newBuilder()...build());
  }

}
