# 该框架是由rxjava+retrofit+MVP+dataBinding搭建

### 封装的内容
   1.找控件用databinding代替butterknife<br>  
   2.沉浸式状态栏的封装（已解决输入法会把头部订出去的BUG）<br>  
   3.通用头部toolbar的封装,继承baseAcrtivity默认添加头部,如有特殊布局可设置不添加。<br>  
     在activity里面重写这个方法并返回false即可设置不添加
   ```
   /**
     * 不添加通用的头部
     * @return
     */
    @Override
    protected boolean isTitleBar() {
        return false;
    }
    ```
   
   4.通用状态布局(数据加载中、暂无数据等)的封装,一行代码切换状态。
   ```
        mStateModel.setEmptyState(EmptyState.PROGRESS);//设置页面状态为加载中
        //mStateModel.setEmptyState(EmptyState.NORMAL);//设置页面状态为正常
        //mStateModel.setEmptyState(EmptyState.EMPTY);//设置页面状态为暂无数据
        //EmptyState类里面可自定义添加状态
   ```
   

