package com.mahmoud.belal.postsapp

import com.mahmoud.belal.postsapp.local.AppDatabaseTest
import com.mahmoud.belal.postsapp.local.PostDaoTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    ExampleInstrumentedTest::class,
    AppDatabaseTest::class,
    PostDaoTest::class
)
class PostAppTestSuit