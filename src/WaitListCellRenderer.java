//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.StringTokenizer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

class WaitListCellRenderer extends JLabel implements ListCellRenderer {
    protected static Border m_noFocusBorder;
    protected FontMetrics m_fm = null;
    protected Insets m_insets = new Insets(0, 0, 0, 0);
    protected int m_defaultTab = 50;
    protected int[] m_tabs = null;
    private int count;

    public WaitListCellRenderer() {
        m_noFocusBorder = new EmptyBorder(1, 1, 1, 1);
        this.setOpaque(true);
        this.setBorder(m_noFocusBorder);
        this.count = 0;
    }

    public Component getListCellRendererComponent(JList var1, Object var2, int var3, boolean var4, boolean var5) {
        this.setText(var2.toString());
        this.setBackground(var4 ? var1.getSelectionBackground() : var1.getBackground());
        this.setForeground(var4 ? var1.getSelectionForeground() : var1.getForeground());
        this.setFont(var1.getFont());
        this.setBorder(var5 ? UIManager.getBorder("List.focusCellHighlightBorder") : m_noFocusBorder);
        return this;
    }

    public void setDefaultTab(int var1) {
        this.m_defaultTab = var1;
    }

    public int getDefaultTab() {
        return this.m_defaultTab;
    }

    public void setTabs(int[] var1) {
        this.m_tabs = var1;
    }

    public int[] getTabs() {
        return this.m_tabs;
    }

    public int getTab(int var1) {
        if (this.m_tabs == null) {
            return this.m_defaultTab * var1;
        } else {
            int var2 = this.m_tabs.length;
            return var1 >= 0 && var1 < var2 ? this.m_tabs[var1] : this.m_tabs[var2 - 1] + this.m_defaultTab * (var1 - var2 - 1);
        }
    }

    public void paint(Graphics var1) {
        this.m_fm = var1.getFontMetrics();
        var1.setColor(this.getBackground());
        var1.fillRect(0, 0, this.getWidth(), this.getHeight());
        this.getBorder().paintBorder(this, var1, 0, 0, this.getWidth(), this.getHeight());
        var1.setColor(this.getForeground());
        var1.setFont(this.getFont());
        this.m_insets = this.getInsets();
        int var2 = this.m_insets.left;
        int var3 = this.m_insets.top + this.m_fm.getAscent();

        int var6;
        for(StringTokenizer var4 = new StringTokenizer(this.getText(), "="); var4.hasMoreTokens(); var2 = this.getTab(var6)) {
            String var5 = var4.nextToken();
            var1.drawString(var5, var2, var3);
            var2 += this.m_fm.stringWidth(var5);
            if (!var4.hasMoreTokens()) {
                break;
            }

            for(var6 = 0; var2 >= this.getTab(var6); ++var6) {
            }
        }

    }
}
