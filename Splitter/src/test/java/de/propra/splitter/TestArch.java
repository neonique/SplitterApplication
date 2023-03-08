package de.propra.splitter;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import de.propra.splitter.stereotypes.AggregateRoot;
import de.propra.splitter.stereotypes.DTO;

@AnalyzeClasses(packagesOf = SplitterApplication.class, importOptions = {ImportOption.DoNotIncludeTests.class})
public class TestArch {

  @ArchTest
  ArchRule onionTest = onionArchitecture()
      .domainModels("..domain.model..")
      .domainServices("..domain.service..")
      .applicationServices("..applicationservice..")
      .adapter("web","..web..");
      //.adapter("db", "..repositories..")
      //.adapter("config", "..config..");

  @ArchTest
  ArchRule onlyAggregateRootCanBeAccessedFromOutsideDomain = classes().that()
      .resideInAPackage("..domain.model..").and()
      .areNotAnnotatedWith(AggregateRoot.class).and().areNotAnnotatedWith(DTO.class).should()
      .onlyBeAccessed().byClassesThat().resideInAPackage("..domain.model..");
/*

  @ArchTest
  ArchRule DTOsCanBeAccessedFromAnywhere = classes().that()
      .resideInAPackage("..domain.model..").and()
      .areNotAnnotatedWith(AggregateRoot.class).should()
      .onlyBeAccessed().byClassesThat().resideInAPackage("..domain.model..");

 */
}
