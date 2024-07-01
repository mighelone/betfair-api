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
import com.betfair.esa.swagger.model.RequestMessage;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
/**
 * AuthenticationMessage
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2024-07-01T08:43:34.380256+01:00[Europe/London]")
public class AuthenticationMessage extends RequestMessage {
  @SerializedName("session")
  private String session = null;

  @SerializedName("appKey")
  private String appKey = null;

  public AuthenticationMessage session(String session) {
    this.session = session;
    return this;
  }

   /**
   * Get session
   * @return session
  **/
  @Schema(description = "")
  public String getSession() {
    return session;
  }

  public void setSession(String session) {
    this.session = session;
  }

  public AuthenticationMessage appKey(String appKey) {
    this.appKey = appKey;
    return this;
  }

   /**
   * Get appKey
   * @return appKey
  **/
  @Schema(description = "")
  public String getAppKey() {
    return appKey;
  }

  public void setAppKey(String appKey) {
    this.appKey = appKey;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuthenticationMessage authenticationMessage = (AuthenticationMessage) o;
    return Objects.equals(this.session, authenticationMessage.session) &&
        Objects.equals(this.appKey, authenticationMessage.appKey) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(session, appKey, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuthenticationMessage {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    session: ").append(toIndentedString(session)).append("\n");
    sb.append("    appKey: ").append(toIndentedString(appKey)).append("\n");
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
