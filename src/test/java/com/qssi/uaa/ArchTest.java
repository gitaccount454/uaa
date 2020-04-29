package com.qssi.uaa;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.qssi.uaa");

        noClasses()
            .that()
                .resideInAnyPackage("com.qssi.uaa.service..")
            .or()
                .resideInAnyPackage("com.qssi.uaa.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.qssi.uaa.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
