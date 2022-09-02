package net.venarge.terracraft.util.side;

public enum Dist {
    CLIENT,
    DEDICATED_SERVER;

    private Dist(){
    }

    public boolean isDedicatedServer(){ return !this.isClient (); }

    public boolean isClient(){return this == CLIENT;}

}
