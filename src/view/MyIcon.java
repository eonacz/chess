package view;

import java.awt.Image;
import javax.swing.ImageIcon;

public class MyIcon extends ImageIcon{
    private static final long serialVersionUID = 1L;
    protected String pathname;//表示存储地址
    protected boolean hasmoved=false;//表示棋子是否已经走动，以后会用到的
    public MyIcon(String str) {
        super(str);
        this.pathname=str;
    }
    public MyIcon(Image img) {
        super(img);
    }
    public String getPath() {
        return pathname;
    }
}
