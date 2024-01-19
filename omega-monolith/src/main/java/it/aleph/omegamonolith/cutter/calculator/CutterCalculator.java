package it.aleph.omegamonolith.cutter.calculator;
@FunctionalInterface
public interface CutterCalculator {
    String calculate(String accessPoint, Integer expansionPrecision);
}
