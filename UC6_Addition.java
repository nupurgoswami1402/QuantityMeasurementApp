public class UC6_Addition {

    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CM(0.0328084);

        private final double toFeet;

        LengthUnit(double toFeet) {
            this.toFeet = toFeet;
        }

        public double toFeet(double value) {
            return value * toFeet;
        }

        public double fromFeet(double feet) {
            return feet / toFeet;
        }
    }

    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid value");
            }
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        // ✅ Instance add (result in THIS unit)
        public Quantity add(Quantity other) {
            if (other == null) {
                throw new IllegalArgumentException("Other cannot be null");
            }

            double sumFeet = this.toFeet() + other.toFeet();
            double result = unit.fromFeet(sumFeet);

            return new Quantity(result, this.unit);
        }

        // ✅ Static add (explicit target unit)
        public static Quantity add(Quantity a, Quantity b, LengthUnit target) {
            if (a == null || b == null || target == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            double sumFeet = a.toFeet() + b.toFeet();
            double result = target.fromFeet(sumFeet);

            return new Quantity(result, target);
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    public static void main(String[] args) {

        Quantity f1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity i12 = new Quantity(12.0, LengthUnit.INCH);
        Quantity y1 = new Quantity(1.0, LengthUnit.YARD);
        Quantity cm = new Quantity(2.54, LengthUnit.CM);

        // Instance add
        System.out.println("Feet + Inches: " + f1.add(i12)); // 2 FEET

        // Reverse (unit changes)
        System.out.println("Inches + Feet: " + i12.add(f1)); // 24 INCH

        // Yard + Feet
        System.out.println("Yard + Feet: " + y1.add(new Quantity(3.0, LengthUnit.FEET))); // 2 YARD

        // CM + Inch
        System.out.println("CM + Inch: " + cm.add(new Quantity(1.0, LengthUnit.INCH))); // ~5.08 CM

        // Static add with target
        System.out.println("Target Feet: " +
                Quantity.add(f1, i12, LengthUnit.FEET));

        // Zero + negative
        System.out.println("Zero: " +
                f1.add(new Quantity(0.0, LengthUnit.INCH)));

        System.out.println("Negative: " +
                f1.add(new Quantity(-2.0, LengthUnit.FEET)));
    }
}
