package com.arcadiogames.planetadventure.Logic;

/**
 * Created by Andres on 05/05/2017.
 */

public enum Dir {
    N, E,  S,  W ;

//    public static Dir rotate(Dir dir) {
//        Dir dir2 = dir;
//        switch (dir) {
//            case N:
//                dir2 = NE;
//                break;
//            case NE:
//                dir2 = E;
//                break;
//            case E:
//                dir2 = SE;
//                break;
//            case SE:
//                dir2 = S;
//                break;
//            case S:
//                dir2 = SW;
//                break;
//            case SW:
//                dir2 = W;
//                break;
//            case W:
//                dir2 = NW;
//                break;
//            case NW:
//                dir2 = N;
//                break;
//        }
//        return dir2;
//    }
//
//    public static Dir predecesor(Dir dir) {
//        Dir dir2 = dir;
//        switch (dir) {
//            case N:
//                dir2 = NW;
//                break;
//            case NE:
//                dir2 = N;
//                break;
//            case E:
//                dir2 = NE;
//                break;
//            case SE:
//                dir2 = E;
//                break;
//            case S:
//                dir2 = SE;
//                break;
//            case SW:
//                dir2 = S;
//                break;
//            case W:
//                dir2 = SW;
//                break;
//            case NW:
//                dir2 = W;
//                break;
//        }
//        return dir2;
//    }
//
    public static float toAngle(Dir dir){
        float angle = 0;
        switch (dir) {
            case N:
                angle = 0;
                break;
            case E:
                angle = (float) (Math.PI/2);
                break;

            case S:
                angle = (float) (Math.PI);
                break;

            case W:
                angle = (float) (3*Math.PI/2);
                break;
        }
        return angle;
    }

}