# MasterMind
[AGH-LAB] Group project - implementing simple game

## Used technologies and design patterns

- Hibernate - Data Mapper (used local Derby server)
- JavaFX - MVC/MVP

## How to start?

Just run gradle configuration after turning on Derby server on local machine (localhost).
In case of problem with non-existing database navigate to src/main/resources/hibernate.cfg.xml and change property named "hibernate.hbm2ddl.auto" from "update" to "create" in first use.
