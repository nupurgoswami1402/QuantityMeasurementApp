public class UC4_ExtendedUnits {

    // Extended Enum (base = FEET)
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CM(0.0328084); // 1 cm = 0.0328084 feet

        private final double toFeet;

        LengthUnit(double toFeet) {
            this.toFeet = toFeet;
        }

        public double toFeet(double value) {
            return value * toFeet;
        }
    }

    // Generic Quantity class (same as UC3)
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

    // Test cases
    public static void main(String[] args) {

        Quantity q1 = new Quantity(1.0, LengthUnit.YARD);
        Quantity q2 = new Quantity(3.0, LengthUnit.FEET);
        Quantity q3 = new Quantity(36.0, LengthUnit.INCH);

        Quantity q4 = new Quantity(1.0, LengthUnit.CM);
        Quantity q5 = new Quantity(0.393701, LengthUnit.INCH);

        System.out.println("Yard to Feet: " + q1.equals(q2));     // true
        System.out.println("Yard to Inch: " + q1.equals(q3));     // true
        System.out.println("CM to Inch: " + q4.equals(q5));       // true
        System.out.println("Same Unit CM: " + q4.equals(new Quantity(1.0, LengthUnit.CM))); // true
        System.out.println("Different: " + q1.equals(q4));        // false
    }
}
