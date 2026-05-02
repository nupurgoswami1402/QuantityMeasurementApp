public class QuantityMeasurementApp {
    public static void main(String[] args) {
package quantitymeasurement;

import java.util.Objects;

public class QuantityLength {

    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {

        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        if (Double.isNaN(value) || Double.isInfinite(value))
            throw new IllegalArgumentException("Invalid value");

        this.value = value;
        this.unit = unit;
    }

    public QuantityLength convertTo(LengthUnit targetUnit) {

        double baseValue = unit.convertToBaseUnit(this.value);
        double convertedValue = targetUnit.convertFromBaseUnit(baseValue);

        return new QuantityLength(convertedValue, targetUnit);
    }

    public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {

        double base1 = this.unit.convertToBaseUnit(this.value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        double sumBase = base1 + base2;

        double result = targetUnit.convertFromBaseUnit(sumBase);

        return new QuantityLength(result, targetUnit);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        QuantityLength other = (QuantityLength) obj;

        double base1 = this.unit.convertToBaseUnit(this.value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        return Double.compare(base1, base2) == 0;
    }
}
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength result = q1.convertTo(LengthUnit.INCHES);

        System.out.println(result); // expect ~12 inches
    }
}
