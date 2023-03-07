package de.propra.splitter;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packagesOf = SplitterApplication.class)
public class TestArch {

  @ArchTest
  ArchRule onionTest = onionArchitecture()
      .domainModels("..domain..")
      .domainServices("..domain..")
      .applicationServices("..services..")
      .adapter("web","..web..");
      //.adapter("db", "..repositories..")
      //.adapter("config", "..config..");

 /* @ArchTest
  ArchRule servicesOnlyUseAggregateRoot = classes()
      .

  */

}
