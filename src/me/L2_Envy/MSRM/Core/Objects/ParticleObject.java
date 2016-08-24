package me.L2_Envy.MSRM.Core.Objects;

import org.inventivetalent.particle.ParticleEffect;

/**
 * Created by Daniel on 7/24/2016.
 */
public class ParticleObject {
    private float offSetX;
    private float offSetY;
    private float offSetZ;
    private float speed;
    private ParticleEffect particle;
    private int amount;

    public ParticleObject(String particle, int amount, float offSetX,
                              float offSetY, float offSetZ, float speed) {
        this.amount = amount;
        this.offSetX = offSetX;
        this.offSetY = offSetY;
        this.offSetZ = offSetZ;
        this.speed = speed;
        this.particle =  ParticleEffect.valueOf(particle);
    }

    public float getOffSetX() {
        return offSetX;
    }

    public float getOffSetY() {
        return offSetY;
    }

    public float getOffSetZ() {
        return offSetZ;
    }

    public float getSpeed() {
        return speed;
    }

    public ParticleEffect getParticle() {
        return particle;
    }

    public int getAmount() {
        return amount;
    }
}
