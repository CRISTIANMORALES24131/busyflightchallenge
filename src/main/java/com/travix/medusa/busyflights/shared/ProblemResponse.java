package com.travix.medusa.busyflights.shared;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.Builder;
import lombok.Getter;
import org.zalando.problem.Problem;

// Extending Problem from org.zalando.problem library as an approach for returning errors in JSON responses
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
public class ProblemResponse implements Problem {
  private String message;
  private Integer statusCode;
  @JsonIgnore
  private String path;

  @Override
  public URI getType() {
    URI typeUri;
    try {
      typeUri = new URI(path);
    } catch (URISyntaxException e) {
      typeUri = DEFAULT_TYPE;
    }
    return typeUri;
  }
}
