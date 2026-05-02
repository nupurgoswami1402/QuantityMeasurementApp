public class UC3_QuantityEquality {

    // Step 1: Enum for units
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0);

        private final double toFeet;

        LengthUnit(double toFeet) {
            this.toFeet = toFeet;
        }

        public double toFeet(double value) {
            return value * toFeet;
        }
    }

    // Step 2: Generic Quantity class
    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            this.value = value;
            this.unit = unit;
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || this.getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;

            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }
    }

    // Step 3: Main method (test cases)
    public static void main(String[] args) {

        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);

        Quantity q3 = new Quantity(1.0, LengthUnit.INCH);
        Quantity q4 = new Quantity(1.0, LengthUnit.INCH);

        Quantity q5 = new Quantity(2.0, LengthUnit.FEET);

        System.out.println("Feet to Inches Equal: " + q1.equals(q2)); // true
        System.out.println("Inch to Inch Equal: " + q3.equals(q4));   // true
        System.out.println("Feet Different: " + q1.equals(q5));       // false
        System.out.println("Same Reference: " + q1.equals(q1));       // true
        System.out.println("Null Check: " + q1.equals(null));         // false
    }
}
