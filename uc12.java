// UC12: Subtraction and Division Operations on Quantity Measurements

interface IMeasurable {
    double getConversionFactor();
    double convertToBaseUnit(double value);
    double convertFromBaseUnit(double baseValue);
    String getUnitName();
}

// ---------------- LENGTH ----------------
enum LengthUnit implements IMeasurable {
    FEET(1.0),
    INCHES(1.0 / 12),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double factor;

    LengthUnit(double factor) {
        this.factor = factor;
    }

    public double getConversionFactor() {
        return factor;
    }

    public double convertToBaseUnit(double value) {
        return value * factor;
    }

    public double convertFromBaseUnit(double baseValue) {
        return baseValue / factor;
    }

    public String getUnitName() {
        return name();
    }
}

// ---------------- WEIGHT ----------------
enum WeightUnit implements IMeasurable {
    KILOGRAM(1.0),
    GRAM(0.001);

    private final double factor;

    WeightUnit(double factor) {
        this.factor = factor;
    }

    public double getConversionFactor() {
        return factor;
    }

    public double convertToBaseUnit(double value) {
        return value * factor;
    }

    public double convertFromBaseUnit(double baseValue) {
        return baseValue / factor;
    }

    public String getUnitName() {
        return name();
    }
}

// ---------------- VOLUME ----------------
enum VolumeUnit implements IMeasurable {
    LITRE(1.0),
    MILLILITRE(0.001),
    GALLON(3.78541);

    private final double factor;

    VolumeUnit(double factor) {
        this.factor = factor;
    }

    public double getConversionFactor() {
        return factor;
    }

    public double convertToBaseUnit(double value) {
        return value * factor;
    }

    public double convertFromBaseUnit(double baseValue) {
        return baseValue / factor;
    }

    public String getUnitName() {
        return name();
    }
}

// ---------------- GENERIC QUANTITY ----------------
class Quantity<U extends IMeasurable> {
    private final double value;
    private final U unit;
    private static final double EPSILON = 0.0001;

    public Quantity(double value, U unit) {
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
        if (Double.isNaN(value) || Double.isInfinite(value))
            throw new IllegalArgumentException("Invalid value");
        this.value = value;
        this.unit = unit;
    }

    public Quantity<U> convertTo(U targetUnit) {
        if (targetUnit == null) throw new IllegalArgumentException("Target unit null");

        double base = unit.convertToBaseUnit(value);
        double result = targetUnit.convertFromBaseUnit(base);
        return new Quantity<>(round(result), targetUnit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        validate(other, targetUnit);

        double sum = unit.convertToBaseUnit(value)
                + other.unit.convertToBaseUnit(other.value);

        double result = targetUnit.convertFromBaseUnit(sum);
        return new Quantity<>(round(result), targetUnit);
    }

    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    // ---------------- SUBTRACTION ----------------
    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        validate(other, targetUnit);

        double diff = unit.convertToBaseUnit(value)
                - other.unit.convertToBaseUnit(other.value);

        double result = targetUnit.convertFromBaseUnit(diff);
        return new Quantity<>(round(result), targetUnit);
    }

    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    // ---------------- DIVISION ----------------
    public double divide(Quantity<U> other) {
        validate(other, this.unit);

        double divisor = other.unit.convertToBaseUnit(other.value);
        if (Math.abs(divisor) < EPSILON)
            throw new ArithmeticException("Division by zero");

        double dividend = unit.convertToBaseUnit(value);
        return dividend / divisor;
    }

    private void validate(Quantity<U> other, U targetUnit) {
        if (other == null) throw new IllegalArgumentException("Other is null");
        if (targetUnit == null) throw new IllegalArgumentException("Target unit null");

        if (!unit.getClass().equals(other.unit.getClass()))
            throw new IllegalArgumentException("Cross-category operation not allowed");
    }

    private double round(double val) {
        return Math.round(val * 100.0) / 100.0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Quantity<?>)) return false;

        Quantity<?> other = (Quantity<?>) obj;

        if (!unit.getClass().equals(other.unit.getClass()))
            return false;

        double thisBase = unit.convertToBaseUnit(value);
        double otherBase = ((IMeasurable) other.unit)
                .convertToBaseUnit(other.value);

        return Math.abs(thisBase - otherBase) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(unit.convertToBaseUnit(value));
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit.getUnitName() + ")";
    }
}

// ---------------- MAIN CLASS ----------------
public class uc12 {

    public static void main(String[] args) {

        // -------- LENGTH --------
        Quantity<LengthUnit> l1 = new Quantity<>(10, LengthUnit.FEET);
        Quantity<LengthUnit> l2 = new Quantity<>(6, LengthUnit.INCHES);

        System.out.println("Subtraction (Length): " + l1.subtract(l2));
        System.out.println("Subtraction (Target Inches): " + l1.subtract(l2, LengthUnit.INCHES));
        System.out.println("Division (Length): " + l1.divide(new Quantity<>(2, LengthUnit.FEET)));

        // -------- WEIGHT --------
        Quantity<WeightUnit> w1 = new Quantity<>(10, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(5000, WeightUnit.GRAM);

        System.out.println("Subtraction (Weight): " + w1.subtract(w2));
        System.out.println("Division (Weight): " + w1.divide(new Quantity<>(5, WeightUnit.KILOGRAM)));

        // -------- VOLUME --------
        Quantity<VolumeUnit> v1 = new Quantity<>(5, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(500, VolumeUnit.MILLILITRE);

        System.out.println("Subtraction (Volume): " + v1.subtract(v2));
        System.out.println("Subtraction (Target mL): " + v1.subtract(v2, VolumeUnit.MILLILITRE));
        System.out.println("Division (Volume): " + v1.divide(new Quantity<>(10, VolumeUnit.LITRE)));

        // -------- EDGE CASES --------
        System.out.println("Negative Result: " +
                new Quantity<>(5, LengthUnit.FEET)
                        .subtract(new Quantity<>(10, LengthUnit.FEET)));

        System.out.println("Zero Result: " +
                new Quantity<>(1, VolumeUnit.LITRE)
                        .subtract(new Quantity<>(1000, VolumeUnit.MILLILITRE)));

        // -------- CROSS CATEGORY (EXCEPTION) --------
        try {
            l1.subtract((Quantity) w1); // unsafe cast to demonstrate
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            l1.divide((Quantity) w1);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
