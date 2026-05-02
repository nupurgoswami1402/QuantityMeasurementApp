public class UC7_TargetAddition {

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

        // ✅ UC6 style (implicit target = this.unit)
        public Quantity add(Quantity other) {
            return add(this, other, this.unit);
        }

        // ✅ UC7 style (explicit target unit)
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

        // 🔥 UC7 examples
        System.out.println("Target FEET: " +
                Quantity.add(f1, i12, LengthUnit.FEET));   // 2 FEET

        System.out.println("Target INCH: " +
                Quantity.add(f1, i12, LengthUnit.INCH));   // 24 INCH

        System.out.println("Target YARD: " +
                Quantity.add(f1, i12, LengthUnit.YARD));   // ~0.667 YARD

        System.out.println("Target CM: " +
                Quantity.add(f1, i12, LengthUnit.CM));     // ~60.96 CM

        // Edge cases
        System.out.println("Zero: " +
                Quantity.add(f1, new Quantity(0, LengthUnit.INCH), LengthUnit.YARD));

        System.out.println("Negative: " +
                Quantity.add(f1, new Quantity(-2, LengthUnit.FEET), LengthUnit.INCH));
    }
}
