package de.propra.splitter;

import static com.tngtech.archunit.library.Architectures.onionArchitecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packagesOf = SplitterApplication.class)
public class TestArch {

  @ArchTest
  ArchRule onionTest = onionArchitecture()
      .domainModels("..domain..")
      //.domainServices("..domain.Gruppe..")
      .applicationServices("..services..")
      .adapter("web","..web..");
      //.adapter("db", "..repositories..")
      //.adapter("config", "..config..");

}