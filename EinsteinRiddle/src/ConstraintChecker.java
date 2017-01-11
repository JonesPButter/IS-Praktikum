public class ConstraintChecker {

    public static boolean check(ConstraintType how, int house1, int house2) {
        switch (how) {
            case equal:
                return house1 == house2;
            case unequal:
                return house1 != house2;
            case leftTo:
                return house1 + 1 == house2 && house1 < 5;
            case rightTo:
                return house1 - 1 == house2 && house2 > 1;
            case nextTo:
                return (Math.abs(house1 - house2) == 1);
            case middle:
                return house1 == 3;
            case first:
                return house1 == 1;
            default:
                throw new RuntimeException("WRONG CHECK " + how);
        }
    }
}
