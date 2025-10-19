package com.algaworks.algashop.ordering.domain.utilility;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochRandomGenerator;
import io.hypersistence.tsid.TSID;
import io.hypersistence.tsid.TSID.Factory;
import java.util.UUID;

public class IdGenerator {

  private static final TimeBasedEpochRandomGenerator timeBasedEpochRandomGenerator
      = Generators.timeBasedEpochRandomGenerator();


  private static final TSID.Factory  tsidfactory =  Factory.INSTANCE;

  private IdGenerator() {
  }

  public static UUID generateTimeBaseUUID() {
    return timeBasedEpochRandomGenerator.generate();
  }


  /*

  TSI_NODE
  TSID_NODE_COUNT

   */

  public static TSID generateTSID() {
    return tsidfactory.generate();
  }
}
