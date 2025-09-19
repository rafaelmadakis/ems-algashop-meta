package com.algaworks.algashop.ordering.domain.utilility;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochRandomGenerator;
import java.util.UUID;

public class IdGenerator {

  private static final TimeBasedEpochRandomGenerator timeBasedEpochRandomGenerator
      = Generators.timeBasedEpochRandomGenerator();

  private IdGenerator() {
  }

  public static UUID generateTimeBaseUUID() {
    return timeBasedEpochRandomGenerator.generate();
  }
}
