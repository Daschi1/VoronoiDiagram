package de.daschi.voronoidiagram.util;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicDouble extends Number {
    private static final long serialVersionUID = -7005029867761849130L;
    private final AtomicLong bits;

    public AtomicDouble() {
        this(0);
    }

    public AtomicDouble(final double initialValue) {
        this.bits = new AtomicLong(Double.doubleToLongBits(initialValue));
    }

    public final void set(final double newValue) {
        this.bits.set(Double.doubleToLongBits(newValue));
    }

    public final double get() {
        return Double.longBitsToDouble(this.bits.get());
    }

    public final double incrementAndGet() {
        this.set(this.get() + 1);
        return this.get();
    }

    @Override
    public int intValue() {
        return (int) this.get();
    }

    @Override
    public long longValue() {
        return (long) this.get();
    }

    @Override
    public float floatValue() {
        return (float) this.get();
    }

    @Override
    public double doubleValue() {
        return this.get();
    }
}
