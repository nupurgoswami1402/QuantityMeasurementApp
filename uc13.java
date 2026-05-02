public class uc13 {

    public static void main(String[] args) {

        // -------- LENGTH --------
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(6.0, LengthUnit.INCHES);

        System.out.println("Subtraction (implicit): " + q1.subtract(q2));
        System.out.println("Subtraction (explicit): " + q1.subtract(q2, LengthUnit.INCHES));
        System.out.println("Division: " + q1.divide(q2));

        // -------- WEIGHT --------
        Quantity<WeightUnit> w1 = new Quantity<>(10.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(5000.0, WeightUnit.GRAM);

        System.out.println("Subtraction: " + w1.subtract(w2));
        System.out.println("Division: " + w1.divide(w2));

        // -------- VOLUME --------
        Quantity<VolumeUnit> v1 = new Quantity<>(5.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);

        System.out.println("Subtraction: " + v1.subtract(v2));
        System.out.println("Division: " + v1.divide(v2));
    }
}
