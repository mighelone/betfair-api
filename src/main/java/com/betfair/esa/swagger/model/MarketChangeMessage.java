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
import com.betfair.esa.swagger.model.MarketChange;
import com.betfair.esa.swagger.model.ResponseMessage;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * MarketChangeMessage
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-07-01T08:43:34.380256+01:00[Europe/London]")
public class MarketChangeMessage extends ResponseMessage {
  /**
   * Change Type - set to indicate the type of change - if null this is a delta)
   */
  @JsonAdapter(CtEnum.Adapter.class)
  public enum CtEnum {
    SUB_IMAGE("SUB_IMAGE"),
    RESUB_DELTA("RESUB_DELTA"),
    HEARTBEAT("HEARTBEAT");

    private String value;

    CtEnum(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    public static CtEnum fromValue(String input) {
      for (CtEnum b : CtEnum.values()) {
        if (b.value.equals(input)) {
          return b;
        }
      }
      return null;
    }
    public static class Adapter extends TypeAdapter<CtEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final CtEnum enumeration) throws IOException {
        jsonWriter.value(String.valueOf(enumeration.getValue()));
      }

      @Override
      public CtEnum read(final JsonReader jsonReader) throws IOException {
        Object value = jsonReader.nextString();
        return CtEnum.fromValue((String)(value));
      }
    }
  }  @SerializedName("ct")
  private CtEnum ct = null;

  @SerializedName("clk")
  private String clk = null;

  @SerializedName("heartbeatMs")
  private Long heartbeatMs = null;

  @SerializedName("pt")
  private Long pt = null;

  @SerializedName("initialClk")
  private String initialClk = null;

  @SerializedName("mc")
  private List<MarketChange> mc = null;

  @SerializedName("conflateMs")
  private Long conflateMs = null;

  /**
   * Segment Type - if the change is split into multiple segments, this denotes the beginning and end of a change, and segments in between. Will be null if data is not segmented
   */
  @JsonAdapter(SegmentTypeEnum.Adapter.class)
  public enum SegmentTypeEnum {
    SEG_START("SEG_START"),
    SEG("SEG"),
    SEG_END("SEG_END");

    private String value;

    SegmentTypeEnum(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    public static SegmentTypeEnum fromValue(String input) {
      for (SegmentTypeEnum b : SegmentTypeEnum.values()) {
        if (b.value.equals(input)) {
          return b;
        }
      }
      return null;
    }
    public static class Adapter extends TypeAdapter<SegmentTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final SegmentTypeEnum enumeration) throws IOException {
        jsonWriter.value(String.valueOf(enumeration.getValue()));
      }

      @Override
      public SegmentTypeEnum read(final JsonReader jsonReader) throws IOException {
        Object value = jsonReader.nextString();
        return SegmentTypeEnum.fromValue((String)(value));
      }
    }
  }  @SerializedName("segmentType")
  private SegmentTypeEnum segmentType = null;

  @SerializedName("status")
  private Integer status = null;

  public MarketChangeMessage ct(CtEnum ct) {
    this.ct = ct;
    return this;
  }

   /**
   * Change Type - set to indicate the type of change - if null this is a delta)
   * @return ct
  **/
  @Schema(description = "Change Type - set to indicate the type of change - if null this is a delta)")
  public CtEnum getCt() {
    return ct;
  }

  public void setCt(CtEnum ct) {
    this.ct = ct;
  }

  public MarketChangeMessage clk(String clk) {
    this.clk = clk;
    return this;
  }

   /**
   * Token value (non-null) should be stored and passed in a MarketSubscriptionMessage to resume subscription (in case of disconnect)
   * @return clk
  **/
  @Schema(description = "Token value (non-null) should be stored and passed in a MarketSubscriptionMessage to resume subscription (in case of disconnect)")
  public String getClk() {
    return clk;
  }

  public void setClk(String clk) {
    this.clk = clk;
  }

  public MarketChangeMessage heartbeatMs(Long heartbeatMs) {
    this.heartbeatMs = heartbeatMs;
    return this;
  }

   /**
   * Heartbeat Milliseconds - the heartbeat rate (may differ from requested: bounds are 500 to 30000)
   * @return heartbeatMs
  **/
  @Schema(description = "Heartbeat Milliseconds - the heartbeat rate (may differ from requested: bounds are 500 to 30000)")
  public Long getHeartbeatMs() {
    return heartbeatMs;
  }

  public void setHeartbeatMs(Long heartbeatMs) {
    this.heartbeatMs = heartbeatMs;
  }

  public MarketChangeMessage pt(Long pt) {
    this.pt = pt;
    return this;
  }

   /**
   * Publish Time (in millis since epoch) that the changes were generated
   * @return pt
  **/
  @Schema(description = "Publish Time (in millis since epoch) that the changes were generated")
  public Long getPt() {
    return pt;
  }

  public void setPt(Long pt) {
    this.pt = pt;
  }

  public MarketChangeMessage initialClk(String initialClk) {
    this.initialClk = initialClk;
    return this;
  }

   /**
   * Token value (non-null) should be stored and passed in a MarketSubscriptionMessage to resume subscription (in case of disconnect)
   * @return initialClk
  **/
  @Schema(description = "Token value (non-null) should be stored and passed in a MarketSubscriptionMessage to resume subscription (in case of disconnect)")
  public String getInitialClk() {
    return initialClk;
  }

  public void setInitialClk(String initialClk) {
    this.initialClk = initialClk;
  }

  public MarketChangeMessage mc(List<MarketChange> mc) {
    this.mc = mc;
    return this;
  }

  public MarketChangeMessage addMcItem(MarketChange mcItem) {
    if (this.mc == null) {
      this.mc = new ArrayList<>();
    }
    this.mc.add(mcItem);
    return this;
  }

   /**
   * MarketChanges - the modifications to markets (will be null on a heartbeat
   * @return mc
  **/
  @Schema(description = "MarketChanges - the modifications to markets (will be null on a heartbeat")
  public List<MarketChange> getMc() {
    return mc;
  }

  public void setMc(List<MarketChange> mc) {
    this.mc = mc;
  }

  public MarketChangeMessage conflateMs(Long conflateMs) {
    this.conflateMs = conflateMs;
    return this;
  }

   /**
   * Conflate Milliseconds - the conflation rate (may differ from that requested if subscription is delayed)
   * @return conflateMs
  **/
  @Schema(description = "Conflate Milliseconds - the conflation rate (may differ from that requested if subscription is delayed)")
  public Long getConflateMs() {
    return conflateMs;
  }

  public void setConflateMs(Long conflateMs) {
    this.conflateMs = conflateMs;
  }

  public MarketChangeMessage segmentType(SegmentTypeEnum segmentType) {
    this.segmentType = segmentType;
    return this;
  }

   /**
   * Segment Type - if the change is split into multiple segments, this denotes the beginning and end of a change, and segments in between. Will be null if data is not segmented
   * @return segmentType
  **/
  @Schema(description = "Segment Type - if the change is split into multiple segments, this denotes the beginning and end of a change, and segments in between. Will be null if data is not segmented")
  public SegmentTypeEnum getSegmentType() {
    return segmentType;
  }

  public void setSegmentType(SegmentTypeEnum segmentType) {
    this.segmentType = segmentType;
  }

  public MarketChangeMessage status(Integer status) {
    this.status = status;
    return this;
  }

   /**
   * Stream status: set to null if the exchange stream data is up to date and 503 if the downstream services are experiencing latencies
   * @return status
  **/
  @Schema(description = "Stream status: set to null if the exchange stream data is up to date and 503 if the downstream services are experiencing latencies")
  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MarketChangeMessage marketChangeMessage = (MarketChangeMessage) o;
    return Objects.equals(this.ct, marketChangeMessage.ct) &&
        Objects.equals(this.clk, marketChangeMessage.clk) &&
        Objects.equals(this.heartbeatMs, marketChangeMessage.heartbeatMs) &&
        Objects.equals(this.pt, marketChangeMessage.pt) &&
        Objects.equals(this.initialClk, marketChangeMessage.initialClk) &&
        Objects.equals(this.mc, marketChangeMessage.mc) &&
        Objects.equals(this.conflateMs, marketChangeMessage.conflateMs) &&
        Objects.equals(this.segmentType, marketChangeMessage.segmentType) &&
        Objects.equals(this.status, marketChangeMessage.status) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ct, clk, heartbeatMs, pt, initialClk, mc, conflateMs, segmentType, status, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MarketChangeMessage {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    ct: ").append(toIndentedString(ct)).append("\n");
    sb.append("    clk: ").append(toIndentedString(clk)).append("\n");
    sb.append("    heartbeatMs: ").append(toIndentedString(heartbeatMs)).append("\n");
    sb.append("    pt: ").append(toIndentedString(pt)).append("\n");
    sb.append("    initialClk: ").append(toIndentedString(initialClk)).append("\n");
    sb.append("    mc: ").append(toIndentedString(mc)).append("\n");
    sb.append("    conflateMs: ").append(toIndentedString(conflateMs)).append("\n");
    sb.append("    segmentType: ").append(toIndentedString(segmentType)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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
