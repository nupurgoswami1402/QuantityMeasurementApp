public class UC5_Conversion {

    // Enum (base = FEET)
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

        public double fromFeet(double feetValue) {
            return feetValue / toFeet;
        }
    }

    // Quantity class
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

        // Instance conversion
        public Quantity convertTo(LengthUnit targetUnit) {
            double feet = this.toFeet();
            double converted = targetUnit.fromFeet(feet);
            return new Quantity(converted, targetUnit);
        }

        // Static conversion API (main requirement)
        public static double convert(double value, LengthUnit source, LengthUnit target) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid value");
            }
            if (source == null || target == null) {
                throw new IllegalArgumentException("Units cannot be null");
            }

            double feet = source.toFeet(value);
            return target.fromFeet(feet);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || this.getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;
            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // Demo methods (overloading)
    public static void demonstrateLengthConversion(double value, LengthUnit from, LengthUnit to) {
        double result = Quantity.convert(value, from, to);
        System.out.println(value + " " + from + " -> " + result + " " + to);
    }

    public static void demonstrateLengthConversion(Quantity q, LengthUnit to) {
        Quantity converted = q.convertTo(to);
        System.out.println(q + " -> " + converted);
    }

    public static void main(String[] args) {

        // Static conversions
        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCH); // 12
        demonstrateLengthConversion(3.0, LengthUnit.YARD, LengthUnit.FEET); // 9
        demonstrateLengthConversion(36.0, LengthUnit.INCH, LengthUnit.YARD); // 1
        demonstrateLengthConversion(1.0, LengthUnit.CM, LengthUnit.INCH); // ~0.393701

        // Instance conversion
        Quantity q = new Quantity(2.0, LengthUnit.YARD);
        demonstrateLengthConversion(q, LengthUnit.FEET);

        // Round-trip check
        double v = 5.0;
        double back = Quantity.convert(
                Quantity.convert(v, LengthUnit.FEET, LengthUnit.INCH),
                LengthUnit.INCH,
                LengthUnit.FEET
        );
        System.out.println("Round trip: " + back);

        // Negative + zero
        System.out.println("Zero: " + Quantity.convert(0.0, LengthUnit.FEET, LengthUnit.INCH));
        System.out.println("Negative: " + Quantity.convert(-1.0, LengthUnit.FEET, LengthUnit.INCH));
    }
}
