package de.propra.splitter;

import static com.tngtech.archunit.library.Architectures.onionArchitecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packagesOf = SplitterApplication.class)
public class TestArch {

  @ArchTest
  ArchRule onionTest = onionArchitecture()
      .domainModels("domain") //TODO: ??? ausfüllen
      //.domainServices("???") //TODO: ??? ausfüllen
      .applicationServices("services") //TODO: ??? ausfüllen
      .adapter("web","..controllers..")
      .adapter("db", "..repositories..")
      .adapter("config", "..config..");

}
