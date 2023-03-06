package de.propra.splitter.domain;

import java.util.Objects;

//GitHub: Username may only contain alphanumeric characters or single hyphens, and cannot begin or end with a hyphen.
public final class Nutzer {

  private final String name;

  public Nutzer(String name) {


    this.name = name;
  }

  public String name() {
    return name;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this)
      return true;
    if (obj == null || obj.getClass() != this.getClass())
      return false;
    var that = (Nutzer) obj;
    return Objects.equals(this.name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "User[" +
        "name=" + name + ']';
  }


}
