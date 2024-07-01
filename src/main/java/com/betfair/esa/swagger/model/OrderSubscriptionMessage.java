/*
 * Betfair: Exchange Streaming API
 * API to receive streamed updates. This is an ssl socket connection of CRLF delimited json messages (see RequestMessage & ResponseMessage)
 *
 * OpenAPI spec version: 1.0.1423
 * Contact: bdp@betfair.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package com.betfair.esa.swagger.model;

import java.util.Objects;
import java.util.Arrays;
import com.betfair.esa.swagger.model.OrderFilter;
import com.betfair.esa.swagger.model.RequestMessage;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
/**
 * OrderSubscriptionMessage
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-07-01T08:43:34.380256+01:00[Europe/London]")
public class OrderSubscriptionMessage extends RequestMessage {
  @SerializedName("segmentationEnabled")
  private Boolean segmentationEnabled = null;

  @SerializedName("orderFilter")
  private OrderFilter orderFilter = null;

  @SerializedName("clk")
  private String clk = null;

  @SerializedName("heartbeatMs")
  private Long heartbeatMs = null;

  @SerializedName("initialClk")
  private String initialClk = null;

  @SerializedName("conflateMs")
  private Long conflateMs = null;

  public OrderSubscriptionMessage segmentationEnabled(Boolean segmentationEnabled) {
    this.segmentationEnabled = segmentationEnabled;
    return this;
  }

   /**
   * Segmentation Enabled - allow the server to send large sets of data in segments, instead of a single block
   * @return segmentationEnabled
  **/
  @Schema(description = "Segmentation Enabled - allow the server to send large sets of data in segments, instead of a single block")
  public Boolean isSegmentationEnabled() {
    return segmentationEnabled;
  }

  public void setSegmentationEnabled(Boolean segmentationEnabled) {
    this.segmentationEnabled = segmentationEnabled;
  }

  public OrderSubscriptionMessage orderFilter(OrderFilter orderFilter) {
    this.orderFilter = orderFilter;
    return this;
  }

   /**
   * Get orderFilter
   * @return orderFilter
  **/
  @Schema(description = "")
  public OrderFilter getOrderFilter() {
    return orderFilter;
  }

  public void setOrderFilter(OrderFilter orderFilter) {
    this.orderFilter = orderFilter;
  }

  public OrderSubscriptionMessage clk(String clk) {
    this.clk = clk;
    return this;
  }

   /**
   * Token value delta (received in MarketChangeMessage) that should be passed to resume a subscription
   * @return clk
  **/
  @Schema(description = "Token value delta (received in MarketChangeMessage) that should be passed to resume a subscription")
  public String getClk() {
    return clk;
  }

  public void setClk(String clk) {
    this.clk = clk;
  }

  public OrderSubscriptionMessage heartbeatMs(Long heartbeatMs) {
    this.heartbeatMs = heartbeatMs;
    return this;
  }

   /**
   * Heartbeat Milliseconds - the heartbeat rate (looped back on initial image after validation: bounds are 500 to 5000)
   * @return heartbeatMs
  **/
  @Schema(description = "Heartbeat Milliseconds - the heartbeat rate (looped back on initial image after validation: bounds are 500 to 5000)")
  public Long getHeartbeatMs() {
    return heartbeatMs;
  }

  public void setHeartbeatMs(Long heartbeatMs) {
    this.heartbeatMs = heartbeatMs;
  }

  public OrderSubscriptionMessage initialClk(String initialClk) {
    this.initialClk = initialClk;
    return this;
  }

   /**
   * Token value (received in initial MarketChangeMessage) that should be passed to resume a subscription
   * @return initialClk
  **/
  @Schema(description = "Token value (received in initial MarketChangeMessage) that should be passed to resume a subscription")
  public String getInitialClk() {
    return initialClk;
  }

  public void setInitialClk(String initialClk) {
    this.initialClk = initialClk;
  }

  public OrderSubscriptionMessage conflateMs(Long conflateMs) {
    this.conflateMs = conflateMs;
    return this;
  }

   /**
   * Conflate Milliseconds - the conflation rate (looped back on initial image after validation: bounds are 0 to 120000)
   * @return conflateMs
  **/
  @Schema(description = "Conflate Milliseconds - the conflation rate (looped back on initial image after validation: bounds are 0 to 120000)")
  public Long getConflateMs() {
    return conflateMs;
  }

  public void setConflateMs(Long conflateMs) {
    this.conflateMs = conflateMs;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderSubscriptionMessage orderSubscriptionMessage = (OrderSubscriptionMessage) o;
    return Objects.equals(this.segmentationEnabled, orderSubscriptionMessage.segmentationEnabled) &&
        Objects.equals(this.orderFilter, orderSubscriptionMessage.orderFilter) &&
        Objects.equals(this.clk, orderSubscriptionMessage.clk) &&
        Objects.equals(this.heartbeatMs, orderSubscriptionMessage.heartbeatMs) &&
        Objects.equals(this.initialClk, orderSubscriptionMessage.initialClk) &&
        Objects.equals(this.conflateMs, orderSubscriptionMessage.conflateMs) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(segmentationEnabled, orderFilter, clk, heartbeatMs, initialClk, conflateMs, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrderSubscriptionMessage {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    segmentationEnabled: ").append(toIndentedString(segmentationEnabled)).append("\n");
    sb.append("    orderFilter: ").append(toIndentedString(orderFilter)).append("\n");
    sb.append("    clk: ").append(toIndentedString(clk)).append("\n");
    sb.append("    heartbeatMs: ").append(toIndentedString(heartbeatMs)).append("\n");
    sb.append("    initialClk: ").append(toIndentedString(initialClk)).append("\n");
    sb.append("    conflateMs: ").append(toIndentedString(conflateMs)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
