# 安卓通用框架

# 该项目是用rxjava+retrofit+MVP+dataBinding搭建

### 封装的内容
   1.找控件用databinding代替butterknife<br>  
   2.沉浸式状态栏的封装（已解决输入法会把头部订出去的BUG）<br>  
   3.通用头部toolbar的封装,继承baseAcrtivity默认添加头部,如有特殊布局可设置不添加。<br>  
   4.通用状态布局(数据加载中、暂无数据等)的封装,一行代码切换状态。
   
   
   ```
    /**
     * 是否显示头部
     *
     * @return
     */
    protected boolean isTitleBar() {
        return true;
    }

    /**
     * 是否加载Prestener
     *
     * @return
     */
    protected boolean isPrestener() {
        return true;
    }
   ```
   

