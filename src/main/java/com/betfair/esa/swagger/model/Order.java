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
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
/**
 * Order
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-07-01T08:43:34.380256+01:00[Europe/London]")
public class Order {
  /**
   * Side - the side of the order. For Line markets a &#x27;B&#x27; bet refers to a SELL line and an &#x27;L&#x27; bet refers to a BUY line.
   */
  @JsonAdapter(SideEnum.Adapter.class)
  public enum SideEnum {
    B("B"),
    L("L");

    private String value;

    SideEnum(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    public static SideEnum fromValue(String input) {
      for (SideEnum b : SideEnum.values()) {
        if (b.value.equals(input)) {
          return b;
        }
      }
      return null;
    }
    public static class Adapter extends TypeAdapter<SideEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final SideEnum enumeration) throws IOException {
        jsonWriter.value(String.valueOf(enumeration.getValue()));
      }

      @Override
      public SideEnum read(final JsonReader jsonReader) throws IOException {
        Object value = jsonReader.nextString();
        return SideEnum.fromValue((String)(value));
      }
    }
  }  @SerializedName("side")
  private SideEnum side = null;

  @SerializedName("sv")
  private Double sv = null;

  /**
   * Persistence Type - whether the order will persist at in play or not (L &#x3D; LAPSE, P &#x3D; PERSIST, MOC &#x3D; Market On Close)
   */
  @JsonAdapter(PtEnum.Adapter.class)
  public enum PtEnum {
    L("L"),
    P("P"),
    MOC("MOC");

    private String value;

    PtEnum(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    public static PtEnum fromValue(String input) {
      for (PtEnum b : PtEnum.values()) {
        if (b.value.equals(input)) {
          return b;
        }
      }
      return null;
    }
    public static class Adapter extends TypeAdapter<PtEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final PtEnum enumeration) throws IOException {
        jsonWriter.value(String.valueOf(enumeration.getValue()));
      }

      @Override
      public PtEnum read(final JsonReader jsonReader) throws IOException {
        Object value = jsonReader.nextString();
        return PtEnum.fromValue((String)(value));
      }
    }
  }  @SerializedName("pt")
  private PtEnum pt = null;

  /**
   * Order Type - the type of the order (L &#x3D; LIMIT, MOC &#x3D; MARKET_ON_CLOSE, LOC &#x3D; LIMIT_ON_CLOSE)
   */
  @JsonAdapter(OtEnum.Adapter.class)
  public enum OtEnum {
    L("L"),
    LOC("LOC"),
    MOC("MOC");

    private String value;

    OtEnum(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    public static OtEnum fromValue(String input) {
      for (OtEnum b : OtEnum.values()) {
        if (b.value.equals(input)) {
          return b;
        }
      }
      return null;
    }
    public static class Adapter extends TypeAdapter<OtEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final OtEnum enumeration) throws IOException {
        jsonWriter.value(String.valueOf(enumeration.getValue()));
      }

      @Override
      public OtEnum read(final JsonReader jsonReader) throws IOException {
        Object value = jsonReader.nextString();
        return OtEnum.fromValue((String)(value));
      }
    }
  }  @SerializedName("ot")
  private OtEnum ot = null;

  @SerializedName("lsrc")
  private String lsrc = null;

  @SerializedName("p")
  private Double p = null;

  @SerializedName("sc")
  private Double sc = null;

  @SerializedName("rc")
  private String rc = null;

  @SerializedName("s")
  private Double s = null;

  @SerializedName("pd")
  private Long pd = null;

  @SerializedName("rac")
  private String rac = null;

  @SerializedName("md")
  private Long md = null;

  @SerializedName("cd")
  private Long cd = null;

  @SerializedName("ld")
  private Long ld = null;

  @SerializedName("sl")
  private Double sl = null;

  @SerializedName("avp")
  private Double avp = null;

  @SerializedName("sm")
  private Double sm = null;

  @SerializedName("rfo")
  private String rfo = null;

  @SerializedName("id")
  private String id = null;

  @SerializedName("bsp")
  private Double bsp = null;

  @SerializedName("rfs")
  private String rfs = null;

  /**
   * Status - the status of the order (E &#x3D; EXECUTABLE, EC &#x3D; EXECUTION_COMPLETE)
   */
  @JsonAdapter(StatusEnum.Adapter.class)
  public enum StatusEnum {
    E("E"),
    EC("EC");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    public static StatusEnum fromValue(String input) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(input)) {
          return b;
        }
      }
      return null;
    }
    public static class Adapter extends TypeAdapter<StatusEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final StatusEnum enumeration) throws IOException {
        jsonWriter.value(String.valueOf(enumeration.getValue()));
      }

      @Override
      public StatusEnum read(final JsonReader jsonReader) throws IOException {
        Object value = jsonReader.nextString();
        return StatusEnum.fromValue((String)(value));
      }
    }
  }  @SerializedName("status")
  private StatusEnum status = null;

  @SerializedName("sr")
  private Double sr = null;

  public Order side(SideEnum side) {
    this.side = side;
    return this;
  }

   /**
   * Side - the side of the order. For Line markets a &#x27;B&#x27; bet refers to a SELL line and an &#x27;L&#x27; bet refers to a BUY line.
   * @return side
  **/
  @Schema(description = "Side - the side of the order. For Line markets a 'B' bet refers to a SELL line and an 'L' bet refers to a BUY line.")
  public SideEnum getSide() {
    return side;
  }

  public void setSide(SideEnum side) {
    this.side = side;
  }

  public Order sv(Double sv) {
    this.sv = sv;
    return this;
  }

   /**
   * Size Voided - the amount of the order that has been voided
   * @return sv
  **/
  @Schema(description = "Size Voided - the amount of the order that has been voided")
  public Double getSv() {
    return sv;
  }

  public void setSv(Double sv) {
    this.sv = sv;
  }

  public Order pt(PtEnum pt) {
    this.pt = pt;
    return this;
  }

   /**
   * Persistence Type - whether the order will persist at in play or not (L &#x3D; LAPSE, P &#x3D; PERSIST, MOC &#x3D; Market On Close)
   * @return pt
  **/
  @Schema(description = "Persistence Type - whether the order will persist at in play or not (L = LAPSE, P = PERSIST, MOC = Market On Close)")
  public PtEnum getPt() {
    return pt;
  }

  public void setPt(PtEnum pt) {
    this.pt = pt;
  }

  public Order ot(OtEnum ot) {
    this.ot = ot;
    return this;
  }

   /**
   * Order Type - the type of the order (L &#x3D; LIMIT, MOC &#x3D; MARKET_ON_CLOSE, LOC &#x3D; LIMIT_ON_CLOSE)
   * @return ot
  **/
  @Schema(description = "Order Type - the type of the order (L = LIMIT, MOC = MARKET_ON_CLOSE, LOC = LIMIT_ON_CLOSE)")
  public OtEnum getOt() {
    return ot;
  }

  public void setOt(OtEnum ot) {
    this.ot = ot;
  }

  public Order lsrc(String lsrc) {
    this.lsrc = lsrc;
    return this;
  }

   /**
   * Lapse Status Reason Code - the reason that some or all of this order has been lapsed (null if no portion of the order is lapsed
   * @return lsrc
  **/
  @Schema(description = "Lapse Status Reason Code - the reason that some or all of this order has been lapsed (null if no portion of the order is lapsed")
  public String getLsrc() {
    return lsrc;
  }

  public void setLsrc(String lsrc) {
    this.lsrc = lsrc;
  }

  public Order p(Double p) {
    this.p = p;
    return this;
  }

   /**
   * Price - the original placed price of the order. Line markets operate at even-money odds of 2.0. However, price for these markets refers to the line positions available as defined by the markets min-max range and interval steps
   * @return p
  **/
  @Schema(description = "Price - the original placed price of the order. Line markets operate at even-money odds of 2.0. However, price for these markets refers to the line positions available as defined by the markets min-max range and interval steps")
  public Double getP() {
    return p;
  }

  public void setP(Double p) {
    this.p = p;
  }

  public Order sc(Double sc) {
    this.sc = sc;
    return this;
  }

   /**
   * Size Cancelled - the amount of the order that has been cancelled
   * @return sc
  **/
  @Schema(description = "Size Cancelled - the amount of the order that has been cancelled")
  public Double getSc() {
    return sc;
  }

  public void setSc(Double sc) {
    this.sc = sc;
  }

  public Order rc(String rc) {
    this.rc = rc;
    return this;
  }

   /**
   * Regulator Code - the regulator of the order
   * @return rc
  **/
  @Schema(description = "Regulator Code - the regulator of the order")
  public String getRc() {
    return rc;
  }

  public void setRc(String rc) {
    this.rc = rc;
  }

  public Order s(Double s) {
    this.s = s;
    return this;
  }

   /**
   * Size - the original placed size of the order
   * @return s
  **/
  @Schema(description = "Size - the original placed size of the order")
  public Double getS() {
    return s;
  }

  public void setS(Double s) {
    this.s = s;
  }

  public Order pd(Long pd) {
    this.pd = pd;
    return this;
  }

   /**
   * Placed Date - the date the order was placed
   * @return pd
  **/
  @Schema(description = "Placed Date - the date the order was placed")
  public Long getPd() {
    return pd;
  }

  public void setPd(Long pd) {
    this.pd = pd;
  }

  public Order rac(String rac) {
    this.rac = rac;
    return this;
  }

   /**
   * Regulator Auth Code - the auth code returned by the regulator
   * @return rac
  **/
  @Schema(description = "Regulator Auth Code - the auth code returned by the regulator")
  public String getRac() {
    return rac;
  }

  public void setRac(String rac) {
    this.rac = rac;
  }

  public Order md(Long md) {
    this.md = md;
    return this;
  }

   /**
   * Matched Date - the date the order was matched (null if the order is not matched)
   * @return md
  **/
  @Schema(description = "Matched Date - the date the order was matched (null if the order is not matched)")
  public Long getMd() {
    return md;
  }

  public void setMd(Long md) {
    this.md = md;
  }

  public Order cd(Long cd) {
    this.cd = cd;
    return this;
  }

   /**
   * Cancelled Date - the date the order was cancelled (null if the order is not cancelled)
   * @return cd
  **/
  @Schema(description = "Cancelled Date - the date the order was cancelled (null if the order is not cancelled)")
  public Long getCd() {
    return cd;
  }

  public void setCd(Long cd) {
    this.cd = cd;
  }

  public Order ld(Long ld) {
    this.ld = ld;
    return this;
  }

   /**
   * Lapsed Date - the date the order was lapsed (null if the order is not lapsed)
   * @return ld
  **/
  @Schema(description = "Lapsed Date - the date the order was lapsed (null if the order is not lapsed)")
  public Long getLd() {
    return ld;
  }

  public void setLd(Long ld) {
    this.ld = ld;
  }

  public Order sl(Double sl) {
    this.sl = sl;
    return this;
  }

   /**
   * Size Lapsed - the amount of the order that has been lapsed
   * @return sl
  **/
  @Schema(description = "Size Lapsed - the amount of the order that has been lapsed")
  public Double getSl() {
    return sl;
  }

  public void setSl(Double sl) {
    this.sl = sl;
  }

  public Order avp(Double avp) {
    this.avp = avp;
    return this;
  }

   /**
   * Average Price Matched - the average price the order was matched at (null if the order is not matched). This value is not meaningful for activity on Line markets and is not guaranteed to be returned or maintained for these markets.
   * @return avp
  **/
  @Schema(description = "Average Price Matched - the average price the order was matched at (null if the order is not matched). This value is not meaningful for activity on Line markets and is not guaranteed to be returned or maintained for these markets.")
  public Double getAvp() {
    return avp;
  }

  public void setAvp(Double avp) {
    this.avp = avp;
  }

  public Order sm(Double sm) {
    this.sm = sm;
    return this;
  }

   /**
   * Size Matched - the amount of the order that has been matched
   * @return sm
  **/
  @Schema(description = "Size Matched - the amount of the order that has been matched")
  public Double getSm() {
    return sm;
  }

  public void setSm(Double sm) {
    this.sm = sm;
  }

  public Order rfo(String rfo) {
    this.rfo = rfo;
    return this;
  }

   /**
   * Order Reference - the customer&#x27;s order reference for this order (empty string if one was not set)
   * @return rfo
  **/
  @Schema(description = "Order Reference - the customer's order reference for this order (empty string if one was not set)")
  public String getRfo() {
    return rfo;
  }

  public void setRfo(String rfo) {
    this.rfo = rfo;
  }

  public Order id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Bet Id - the id of the order
   * @return id
  **/
  @Schema(description = "Bet Id - the id of the order")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Order bsp(Double bsp) {
    this.bsp = bsp;
    return this;
  }

   /**
   * BSP Liability - the BSP liability of the order (null if the order is not a BSP order)
   * @return bsp
  **/
  @Schema(description = "BSP Liability - the BSP liability of the order (null if the order is not a BSP order)")
  public Double getBsp() {
    return bsp;
  }

  public void setBsp(Double bsp) {
    this.bsp = bsp;
  }

  public Order rfs(String rfs) {
    this.rfs = rfs;
    return this;
  }

   /**
   * Strategy Reference - the customer&#x27;s strategy reference for this order (empty string if one was not set)
   * @return rfs
  **/
  @Schema(description = "Strategy Reference - the customer's strategy reference for this order (empty string if one was not set)")
  public String getRfs() {
    return rfs;
  }

  public void setRfs(String rfs) {
    this.rfs = rfs;
  }

  public Order status(StatusEnum status) {
    this.status = status;
    return this;
  }

   /**
   * Status - the status of the order (E &#x3D; EXECUTABLE, EC &#x3D; EXECUTION_COMPLETE)
   * @return status
  **/
  @Schema(description = "Status - the status of the order (E = EXECUTABLE, EC = EXECUTION_COMPLETE)")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public Order sr(Double sr) {
    this.sr = sr;
    return this;
  }

   /**
   * Size Remaining - the amount of the order that is remaining unmatched
   * @return sr
  **/
  @Schema(description = "Size Remaining - the amount of the order that is remaining unmatched")
  public Double getSr() {
    return sr;
  }

  public void setSr(Double sr) {
    this.sr = sr;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Order order = (Order) o;
    return Objects.equals(this.side, order.side) &&
        Objects.equals(this.sv, order.sv) &&
        Objects.equals(this.pt, order.pt) &&
        Objects.equals(this.ot, order.ot) &&
        Objects.equals(this.lsrc, order.lsrc) &&
        Objects.equals(this.p, order.p) &&
        Objects.equals(this.sc, order.sc) &&
        Objects.equals(this.rc, order.rc) &&
        Objects.equals(this.s, order.s) &&
        Objects.equals(this.pd, order.pd) &&
        Objects.equals(this.rac, order.rac) &&
        Objects.equals(this.md, order.md) &&
        Objects.equals(this.cd, order.cd) &&
        Objects.equals(this.ld, order.ld) &&
        Objects.equals(this.sl, order.sl) &&
        Objects.equals(this.avp, order.avp) &&
        Objects.equals(this.sm, order.sm) &&
        Objects.equals(this.rfo, order.rfo) &&
        Objects.equals(this.id, order.id) &&
        Objects.equals(this.bsp, order.bsp) &&
        Objects.equals(this.rfs, order.rfs) &&
        Objects.equals(this.status, order.status) &&
        Objects.equals(this.sr, order.sr);
  }

  @Override
  public int hashCode() {
    return Objects.hash(side, sv, pt, ot, lsrc, p, sc, rc, s, pd, rac, md, cd, ld, sl, avp, sm, rfo, id, bsp, rfs, status, sr);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Order {\n");
    
    sb.append("    side: ").append(toIndentedString(side)).append("\n");
    sb.append("    sv: ").append(toIndentedString(sv)).append("\n");
    sb.append("    pt: ").append(toIndentedString(pt)).append("\n");
    sb.append("    ot: ").append(toIndentedString(ot)).append("\n");
    sb.append("    lsrc: ").append(toIndentedString(lsrc)).append("\n");
    sb.append("    p: ").append(toIndentedString(p)).append("\n");
    sb.append("    sc: ").append(toIndentedString(sc)).append("\n");
    sb.append("    rc: ").append(toIndentedString(rc)).append("\n");
    sb.append("    s: ").append(toIndentedString(s)).append("\n");
    sb.append("    pd: ").append(toIndentedString(pd)).append("\n");
    sb.append("    rac: ").append(toIndentedString(rac)).append("\n");
    sb.append("    md: ").append(toIndentedString(md)).append("\n");
    sb.append("    cd: ").append(toIndentedString(cd)).append("\n");
    sb.append("    ld: ").append(toIndentedString(ld)).append("\n");
    sb.append("    sl: ").append(toIndentedString(sl)).append("\n");
    sb.append("    avp: ").append(toIndentedString(avp)).append("\n");
    sb.append("    sm: ").append(toIndentedString(sm)).append("\n");
    sb.append("    rfo: ").append(toIndentedString(rfo)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    bsp: ").append(toIndentedString(bsp)).append("\n");
    sb.append("    rfs: ").append(toIndentedString(rfs)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    sr: ").append(toIndentedString(sr)).append("\n");
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
