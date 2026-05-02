package quantitymeasurement;

public class UC10 {

    // -------- GENERIC DEMO METHODS --------

    public static void demonstrateEquality(Quantity<?> q1, Quantity<?> q2) {
        System.out.println("Equality: " + q1.equals(q2));
    }

    public static <U extends IMeasurable> void demonstrateConversion(Quantity<U> q, U targetUnit) {
        System.out.println("Conversion: " + q.convertTo(targetUnit));
    }

    public static <U extends IMeasurable> void demonstrateAddition(Quantity<U> q1, Quantity<U> q2, U targetUnit) {
        System.out.println("Addition: " + q1.add(q2, targetUnit));
    }

    // -------- MAIN METHOD --------

    public static void main(String[] args) {

        // ===== LENGTH OPERATIONS =====
        Quantity<LengthUnit> l1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> l2 = new Quantity<>(12.0, LengthUnit.INCHES);

        System.out.println("---- LENGTH ----");
        demonstrateEquality(l1, l2);
        demonstrateConversion(l1, LengthUnit.INCHES);
        demonstrateAddition(l1, l2, LengthUnit.FEET);

        // ===== WEIGHT OPERATIONS =====
        Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);

        System.out.println("---- WEIGHT ----");
        demonstrateEquality(w1, w2);
        demonstrateConversion(w1, WeightUnit.GRAM);
        demonstrateAddition(w1, w2, WeightUnit.KILOGRAM);

        // ===== CROSS CATEGORY CHECK =====
        System.out.println("---- CROSS CATEGORY ----");
        System.out.println("Length vs Weight Equality: " + l1.equals(w1)); // should be false
    }
}
