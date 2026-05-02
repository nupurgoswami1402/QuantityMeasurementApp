package quantitymeasurement;

public class UC9 {

    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

        System.out.println("Equality: " + q1.equals(q2)); // true

        System.out.println("Convert: " + q1.convertTo(LengthUnit.INCHES)); // ~12 inches

        System.out.println("Addition: " + q1.add(q2, LengthUnit.FEET)); // 2 feet
    }
}
