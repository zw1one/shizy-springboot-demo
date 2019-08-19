//package com.shizy.utils;
//
//import com.twitter.snowflake.sequence.IdSequence;
//import com.twitter.snowflake.support.IdSequenceFactory;
//
//public class IdUtil {
//    /**
//     * unique id
//     */
//    public static String uniqueIdStr() {
//        return String.valueOf(uniqueIdLong());
//    }
//
//    public static long uniqueIdLong() {
//        return SequenceHolder.snowSequence.nextId();
//    }
//
//    public static class SequenceHolder {
//
//        public static IdSequence snowSequence = null;
//
//        static {
//            IdSequenceFactory defaultFactory = new IdSequenceFactory();
//
//            // set worker id
//            defaultFactory.setWorkerId(1L);
//            // 2018-03-05
//            defaultFactory.setEpochMillis(1545187068690L);
//
//            // create sequence
//            snowSequence = defaultFactory.create();
//        }
//
//    }
//}
