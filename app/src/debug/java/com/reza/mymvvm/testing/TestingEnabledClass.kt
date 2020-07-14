package com.reza.mymvvm.testing


//  allow classes for mocking, even if they defined as final in release
@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class EnabledClass


// make classes extendable
@EnabledClass
@Target(AnnotationTarget.CLASS)
annotation class TestingEnabledClass