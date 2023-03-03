package de.propra.splitter.domaene;

import java.util.Objects;

//GitHub: Username may only contain alphanumeric characters or single hyphens, and cannot begin or end with a hyphen.
public final class Nutzer {

  private final String name;

  public Nutzer(String name) {
    if(!ueberpruefeName(name)){
      throw new IllegalArgumentException("Nutzername ist nicht konform mit GitHub-Namenskonvention");
    }

    this.name = name;
  }

  private boolean ueberpruefeName(String name) {



    if(name.contains("--") || name.startsWith("-") || name.endsWith("-")){
      return false;
    }

    String validChars = "[A-Za-z0-9-]+";
    if(!name.matches(validChars)){
      return false;
    }

    return true;
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
