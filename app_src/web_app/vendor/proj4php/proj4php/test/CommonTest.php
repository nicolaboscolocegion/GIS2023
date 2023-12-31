<?php

use PHPUnit\Framework\TestCase;
use proj4php\Common;

class CommonTest extends TestCase
{
    public function testSign()
    {
        $this->assertEquals(-1, Common::sign(-111));
        $this->assertEquals(-1, Common::sign(-111.2));
        $this->assertEquals(1, Common::sign(0));
        $this->assertEquals(1, Common::sign(1));
        $this->assertEquals(1, Common::sign(200));
    }

    public function testMsfnz()
    {
        $ret = Common::msfnz(0.12, 0.30, 0.40);
        $this->assertEqualsWithDelta(0.40025945221481, $ret, 0.000001);

        $ret = Common::msfnz(0.2, 0.23, 0.10);
        $this->assertEqualsWithDelta(0.10010596820122, $ret, 0.000001);
    }

    public function testTsfnz()
    {
        $ret = Common::tsfnz(0.12, 0.30, 0.40);
        $this->assertEqualsWithDelta(0.74167840619598, $ret, 0.000001);

        $ret = Common::tsfnz(0.4, 0.10, 0.80);

        $this->assertEqualsWithDelta(1.0330253798791, $ret, 0.000001);
    }

    public function testAsinz()
    {
        $ret = Common::asinz(10);
        $this->assertEqualsWithDelta(1.5707963267949, $ret, 0.000001);

        $ret = Common::asinz(-100);
        $this->assertEqualsWithDelta(-1.5707963267949, $ret, 0.000001);

        $ret = Common::asinz(-240);
        $this->assertEqualsWithDelta(-1.5707963267949, $ret, 0.000001);

        $ret = Common::asinz(-370);
        $this->assertEqualsWithDelta(-1.5707963267949, $ret, 0.000001);

        $ret = Common::asinz(310);
        $this->assertEqualsWithDelta(1.5707963267949, $ret, 0.000001);
    }

    public function teste0fn()
    {
        $ret = Common::e0fn(0.35363122);
        $this->assertEqualsWithDelta(0.90486650238871, $ret, 0.000001);

        $ret = Common::e0fn(0.31245122);
        $this->assertEqualsWithDelta(0.91671521990135, $ret, 0.000001);

        $ret = Common::e0fn(0.1257483412);
        $this->assertEqualsWithDelta(0.96778286074154, $ret, 0.000001);
    }

    public function teste1fn()
    {
        $ret = Common::e1fn(0.112341);
        $this->assertEqualsWithDelta(0.0433733525487, $ret, 0.000001);

        $ret = Common::e1fn(0.12141321122);
        $this->assertEqualsWithDelta(0.0469905908072, $ret, 0.000001);

        $ret = Common::e1fn(0.12544522);
        $this->assertEqualsWithDelta(0.04860400576082, $ret, 0.000001);
    }

    public function teste2fn()
    {
        $ret = Common::e2fn(0.22253223);
        $this->assertEqualsWithDelta(0.00338587145, $ret, 0.000001);

        $ret = Common::e2fn(0.1212);
        $this->assertEqualsWithDelta(0.00093894785718, $ret, 0.000001);

        $ret = Common::e2fn(0.1422);
        $this->assertEqualsWithDelta(0.00131117534683, $ret, 0.000001);
    }
}
