package com.yinhai.medicalbenefit.common.action;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.alibaba.fastjson.JSONArray;
import com.opensymphony.xwork2.Action;
import com.yinhai.sysframework.app.domain.Key;
import com.yinhai.sysframework.app.domain.jsonmodel.OperationBean;
import com.yinhai.sysframework.app.domain.jsonmodel.ResultBean;
import com.yinhai.sysframework.codetable.domain.AppCode;
import com.yinhai.sysframework.codetable.service.CodeTableLocator;
import com.yinhai.sysframework.dto.DTO;
import com.yinhai.sysframework.dto.ParamDTO;
import com.yinhai.sysframework.exception.SysLevelException;
import com.yinhai.sysframework.persistence.PageBean;
import com.yinhai.sysframework.persistence.ibatis.IDao;
import com.yinhai.sysframework.service.BaseService;
import com.yinhai.sysframework.service.ServiceLocator;
import com.yinhai.sysframework.util.StringUtil;
import com.yinhai.sysframework.util.json.JSonFactory;
import com.yinhai.webframework.session.UserSession;
import com.yinhai.yhcip.print.datasource.ReportDataSource;
import com.yinhai.yhcip.print.service.ReportService;
import com.yinhai.yhcip.print.servlet.JasperPrintServlet;

@ParentPackage("ta-default")
public class BaseAction implements Action
{
  static Logger logger = Logger.getLogger(BaseAction.class.getName());
  private static final long serialVersionUID = 2863769505963567954L;
  public static final String BUSINESSID = "___businessId";
  public static final String JSON = "tojson";
  public static final String FILE = "tofile";
  private boolean hasgetdto = false;
  protected HttpServletRequest request = ServletActionContext.getRequest();
  private String ___businessId;
  private ParamDTO dto = new ParamDTO();
  private ParamDTO gridInfo = new ParamDTO();
  private ResultBean resultBean = new ResultBean();

  protected ResultBean setMsg(String paramString)
  {
    this.resultBean.setMsg(paramString);
    return this.resultBean;
  }

  protected ResultBean setMsg(String paramString1, String paramString2)
  {
    this.resultBean.setMsg(paramString1);
    this.resultBean.setMsgType(paramString2);
    return this.resultBean;
  }

  protected ResultBean setSuccess(boolean paramBoolean)
  {
    this.resultBean.setSuccess(paramBoolean);
    return this.resultBean;
  }

  protected ResultBean setData(Map<String, Object> paramMap, boolean paramBoolean)
  {
    if (paramMap != null)
    {
      HashMap localHashMap1 = (HashMap)UserSession.getUserSession(this.request).getCurrentBusiness().getSessionResource("__selectinput_flag_map_");
      if ((localHashMap1 != null) && (localHashMap1.size() > 0))
      {
        HashMap localHashMap2 = new HashMap();
        Iterator localIterator = paramMap.entrySet().iterator();
        while (localIterator.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator.next();
          String str1 = (String)localEntry.getKey();
          Object localObject = localEntry.getValue();
          if ((str1 != null) && (!"".equals(str1)) && (localObject != null) && (!"".equals(localObject)) && (localHashMap1.get(str1) != null))
          {
            String str2 = getCodeDesc((String)localHashMap1.get(str1), String.valueOf(localObject), getDto().getUserInfo().getOrgId());
            if ((str2 != null) && (!"".equals(str2)))
              localHashMap2.put(str1 + "_desc", str2);
          }
        }
        paramMap.putAll(localHashMap2);
      }
      this.resultBean.setData(paramMap, paramBoolean);
    }
    return this.resultBean;
  }

  protected ResultBean setData(String paramString, Object paramObject)
  {
    if ((paramString != null) && (!"".equals(paramString)) && (paramObject != null) && (!"".equals(paramObject)))
    {
      
    }
    return this.resultBean.addData(paramString, paramObject);
  }

  protected ResultBean setInvalidField(String paramString1, String paramString2)
  {
    return this.resultBean.addInvalidField(paramString1, paramString2);
  }

  protected ResultBean setList(String paramString, PageBean paramPageBean)
    throws Exception
  {
    paramPageBean.setGridId(paramString);
    paramPageBean.setList(codeDisplay(paramString, paramPageBean.getList()));
    return this.resultBean.addList(paramString, paramPageBean);
  }

  protected ResultBean setList(String paramString, List paramList)
    throws Exception
  {
    return this.resultBean.addList(paramString, codeDisplay(paramString, paramList));
  }

  protected ResultBean setReadOnly(String paramString)
  {
    return this.resultBean.addOperation(new OperationBean("readonly", paramString));
  }

  protected ResultBean setEnable(String paramString)
  {
    return this.resultBean.addOperation(new OperationBean("enable", paramString));
  }

  protected ResultBean setDisabled(String paramString)
  {
    return this.resultBean.addOperation(new OperationBean("disabled", paramString));
  }

  protected ResultBean setActiveTab(String paramString)
  {
    return this.resultBean.addOperation(new OperationBean("select_tab", paramString));
  }

  protected ResultBean setHideObj(String paramString)
  {
    return this.resultBean.addOperation(new OperationBean("hide", paramString));
  }

  protected ResultBean setUnVisibleObj(String paramString)
  {
    return this.resultBean.addOperation(new OperationBean("unvisible", paramString));
  }

  protected ResultBean setShowObj(String paramString)
  {
    return this.resultBean.addOperation(new OperationBean("show", paramString));
  }

  protected ResultBean resetForm(String paramString)
  {
    return this.resultBean.addOperation(new OperationBean("resetform", paramString));
  }

  protected ResultBean setFocus(String paramString)
  {
    this.resultBean.setFocus(paramString);
    return this.resultBean;
  }

  protected ResultBean setRequired(String paramString)
  {
    return this.resultBean.addOperation(new OperationBean("required", paramString));
  }

  protected ResultBean setDisRequired(String paramString)
  {
    return this.resultBean.addOperation(new OperationBean("disrequired", paramString));
  }

  public ParamDTO getDto()
  {
    if (!this.hasgetdto)
    {
      UserSession localUserSession = UserSession.getUserSession(this.request);
      if(localUserSession !=null){
    	  UserSession.Business localBusiness = localUserSession.getCurrentBusiness();
          if ((localUserSession != null) && (localUserSession.getUser() != null))
            this.dto.setUserInfo(localUserSession.getUser());
          this.dto.setGridInfo(this.gridInfo);
      }
     
      this.hasgetdto = true;
    }
    return this.dto;
  }

  public void setDto(ParamDTO paramParamDTO)
  {
    this.dto = paramParamDTO;
  }

  private Integer getStart(String paramString)
  {
    return getGridInfo().getAsInteger(paramString + "_start");
  }

  private Integer getLimit(String paramString)
  {
    return getGridInfo().getAsInteger(paramString + "_limit");
  }

  protected PageBean queryForPage(String paramString1, String paramString2, Object paramObject, boolean paramBoolean)
  {
    PageBean localPageBean = null;
    Integer localInteger1 = getStart(paramString1);
    Integer localInteger2 = getLimit(paramString1);
    if (paramBoolean)
    {
      localPageBean = getDao().queryForPageWithCount(paramString1, paramString2, paramObject, getDto());
    }
    else
    {
      localPageBean = new PageBean(getDao().queryForPage(paramString2, paramObject, localInteger1.intValue(), localInteger2.intValue()));
      localPageBean.setStart(localInteger1);
      localPageBean.setLimit(localInteger2);
    }
    return localPageBean;
  }

  

  public List<Key> jsonStrToList(String paramString)
  {
    List localList = (List)JSonFactory.json2bean(paramString, ArrayList.class);
    ArrayList localArrayList = new ArrayList();
    if ((localList != null) && (localList.size() > 0))
    {
      if (!(localList.get(0) instanceof Map))
        throw new SysLevelException("json格式字符串反序列化类型不支持：" + paramString);
      for (int i = 0; i < localList.size(); i++)
        localArrayList.add(new Key((Map)localList.get(i)));
    }
    return localArrayList;
  }

  public List<Key> getJsonParamAsList(String paramString)
  {
    String str = this.request.getParameter(paramString);
    if (StringUtil.isEmpty(str))
      return new ArrayList();
    return jsonStrToList(str);
  }

  protected void writeJsonToClient(Object paramObject)
    throws Exception
  {
    if (paramObject == null)
      return;
    String str = "";
    if ((paramObject instanceof PageBean))
    {
      PageBean localObject = (PageBean)paramObject;
      ((PageBean)localObject).setList(codeDisplay(((PageBean)localObject).getGridId(), ((PageBean)localObject).getList()));
      str = JSonFactory.bean2json(localObject);
    }
    else if (((paramObject instanceof String)) || ((paramObject instanceof StringBuffer)) || ((paramObject instanceof StringBuilder)))
    {
      str = paramObject.toString();
    }
    else
    {
      str = JSonFactory.bean2json(paramObject);
    }
    Object localObject = ServletActionContext.getResponse();
    ((HttpServletResponse)localObject).setContentType("text/json; charset=UTF-8");
    PrintWriter localPrintWriter = ((HttpServletResponse)localObject).getWriter();
    localPrintWriter.write(str);
    localPrintWriter.flush();
  }

  private Key getGridDisplayCode(String paramString)
  {
    Object localObject = getGridInfo().get(paramString + "_displayCode");
    if (localObject == null)
      return null;
    String str = "";
    if ((localObject instanceof String[]))
    {
      String[] arrayOfString = (String[])(String[])localObject;
      for (int i = 0; i < arrayOfString.length; i++)
        if (str == "")
          str = arrayOfString[i];
        else
          str = str + "^" + arrayOfString[i];
    }
    else
    {
      str = (String)localObject;
    }
    if (str == "")
      return null;
    return new Key(str);
  }

  protected void setGridDisplayCode(String paramString1, String paramString2, String paramString3)
  {
    Key localKey = getGridDisplayCode(paramString1);
    if ((localKey != null) && (localKey.size() != 0))
    {
      Object localObject;
      if (localKey.size() == 1)
      {
        localObject = localKey.keySet().iterator();
        while (((Iterator)localObject).hasNext())
        {
          String str = (String)((Iterator)localObject).next();
          if (str.equals(paramString2))
            this.gridInfo.put(paramString1 + "_displayCode", paramString2 + "`" + paramString3);
          else
            this.gridInfo.put(paramString1 + "_displayCode", new String[] { paramString2 + "`" + paramString3, str + "`" + localKey.getAsString(str) });
        }
      }
      else
      {
        localObject = localKey.getId().split("^");
        this.gridInfo.put(paramString1 + "_displayCode", localObject);
      }
    }
    else
    {
      this.gridInfo.put(paramString1 + "_displayCode", paramString2 + "`" + paramString3);
    }
  }

  protected List codeDisplay(String paramString, List paramList)
    throws Exception
  {
    if ((paramList == null) || ((paramList != null) && (paramList.size() == 0)) || (paramString == null) || (paramString == ""))
      return paramList;
    Key localKey = getGridDisplayCode(paramString);
    if ((localKey == null) || ((localKey != null) && (localKey.size() == 0)))
      return paramList;
    String str1 = getDto().getUserInfo().getOrgId();
    if ((str1 == null) || ("".equals(str1)))
      str1 = null;
    for (int i = 0; i < paramList.size(); i++)
    {
      Object localObject = paramList.get(i);
      Iterator localIterator = localKey.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        String str3 = localKey.getAsString(str2);
        if (StringUtil.isEmpty(str3))
          str3 = str2;
        String str4 = BeanUtils.getProperty(localObject, str2);
        if ((str4 != null) && (!"".equals(str4.toString())))
        {
          String str5 = CodeTableLocator.getInstance().getCodeDesc(str3.toUpperCase(), str4.toString(), str1);
          if ((localObject instanceof Map))
            ((Map)localObject).put(str2, str5);
          else
            BeanUtils.setProperty(localObject, str2, str5);
        }
        paramList.set(i, localObject);
      }
    }
    return paramList;
  }

  protected List<Key> getSelected(String paramString)
  {
    String str = this.gridInfo.getAsString(paramString + "_selected", null);
    if (StringUtil.isEmpty(str))
      return new ArrayList();
    return jsonStrToList(str);
  }

  protected List<Key> getModified(String paramString)
  {
    String str = this.gridInfo.getAsString(paramString + "_modified", null);
    if (StringUtil.isEmpty(str))
      return new ArrayList();
    return jsonStrToList(str);
  }

  protected List<Key> getRemoved(String paramString)
  {
    String str = this.gridInfo.getAsString(paramString + "_removed", null);
    if (StringUtil.isEmpty(str))
      return new ArrayList();
    return jsonStrToList(str);
  }

  protected List<Key> getAdded(String paramString)
  {
    String str = this.gridInfo.getAsString(paramString + "_added", null);
    if (StringUtil.isEmpty(str))
      return new ArrayList();
    return jsonStrToList(str);
  }

  protected Object getSessionResource(String paramString)
  {
    return UserSession.getUserSession(this.request).getCurrentBusiness().getSessionResource(paramString);
  }

  protected void putSessionResource(String paramString, Object paramObject)
  {
    UserSession.getUserSession(this.request).getCurrentBusiness().putSessionResource(paramString, paramObject);
  }

  protected void removeSessionResource(String paramString)
  {
    UserSession.getUserSession(this.request).getCurrentBusiness().removeSessionResource(paramString);
  }

  protected void writeSuccess()
    throws Exception
  {
    HttpServletResponse localHttpServletResponse = ServletActionContext.getResponse();
    localHttpServletResponse.setContentType("text/json; charset=UTF-8");
    PrintWriter localPrintWriter = localHttpServletResponse.getWriter();
    localPrintWriter.write("{\"success\":true}");
    localPrintWriter.flush();
  }

  protected void writeSuccess(String paramString)
    throws Exception
  {
    HttpServletResponse localHttpServletResponse = ServletActionContext.getResponse();
    localHttpServletResponse.setContentType("text/json; charset=UTF-8");
    PrintWriter localPrintWriter = localHttpServletResponse.getWriter();
    localPrintWriter.write("{\"success\":true,\"msg\":\"" + paramString + "\"}");
    localPrintWriter.flush();
  }

  protected void writeFailure()
    throws Exception
  {
    HttpServletResponse localHttpServletResponse = ServletActionContext.getResponse();
    localHttpServletResponse.setContentType("text/json; charset=UTF-8");
    PrintWriter localPrintWriter = localHttpServletResponse.getWriter();
    localPrintWriter.write("{\"success\":false}");
    localPrintWriter.flush();
  }

  protected void writeFailure(String paramString)
    throws Exception
  {
    HttpServletResponse localHttpServletResponse = ServletActionContext.getResponse();
    localHttpServletResponse.setContentType("text/json; charset=UTF-8");
    PrintWriter localPrintWriter = localHttpServletResponse.getWriter();
    localPrintWriter.write("{\"success\":false,\"msg\":\"" + paramString + "\"}");
    localPrintWriter.flush();
  }

  protected IDao getDao()
  {
    return (IDao)ServiceLocator.getService("dao");
  }

  protected Object getService(String paramString)
  {
    return ServiceLocator.getService(paramString);
  }

  public String get___businessId()
  {
    return this.___businessId;
  }

  public void set___businessId(String paramString)
  {
    this.___businessId = paramString;
  }

  protected String getCodeDesc(String paramString1, String paramString2, String paramString3)
  {
    BaseService localBaseService = (BaseService)getService("baseService");
    return localBaseService.getCodeDesc(paramString1, paramString2, paramString3);
  }

  public List<AppCode> getCodeList(String paramString1, String paramString2)
  {
    BaseService localBaseService = (BaseService)getService("baseService");
    return localBaseService.getCodeList(paramString1, paramString2);
  }

  public String execute()
    throws Exception
  {
    return "success";
  }

  public ResultBean getResultBean()
  {
    return this.resultBean;
  }

  public void setResultBean(ResultBean paramResultBean)
  {
    this.resultBean = paramResultBean;
  }

  public ParamDTO getGridInfo()
  {
    return this.gridInfo;
  }

  public void setGridInfo(ParamDTO paramParamDTO)
  {
    this.gridInfo = paramParamDTO;
  }

  public void addPrintData(String paramString, Map paramMap, List paramList)
    throws JRException
  {
    if (null == paramList)
      paramList = new ArrayList();
    if (0 == paramList.size())
      paramList.add(new HashMap());
    this.request.setAttribute(JasperPrintServlet.REPORTNAME, paramString);
    JasperPrint localJasperPrint1 = (JasperPrint)this.request.getAttribute(paramString);
    JasperReport localJasperReport = null;
    if (null == localJasperPrint1)
    {
      localJasperReport = (JasperReport)JRLoader.loadObject(((ReportService)getService(JasperPrintServlet.REPORTSERVICESTR)).getReportTemplateInputStream(paramString));
      localJasperPrint1 = JasperFillManager.fillReport(localJasperReport, paramMap, new ReportDataSource(this.request, paramList));
      this.request.setAttribute(paramString, localJasperPrint1);
    }
    else
    {
      localJasperReport = (JasperReport)JRLoader.loadObject(((ReportService)getService(JasperPrintServlet.REPORTSERVICESTR)).getReportTemplateInputStream(paramString));
      JasperPrint localJasperPrint2 = JasperFillManager.fillReport(localJasperReport, paramMap, new ReportDataSource(this.request, paramList));
      int i = localJasperPrint2.getPages().size();
      for (int j = 0; j < i; j++)
        localJasperPrint1.addPage((JRPrintPage)localJasperPrint2.getPages().get(j));
    }
  }

  public void addPrintViewData(String paramString, Map paramMap, List paramList)
    throws JRException
  {
    this.request.setAttribute(JasperPrintServlet.PRINT_PREVIEW_FLAG, "1");
    addPrintData(paramString, paramMap, paramList);
  }

  public void addPrintBackgroundViewData(String paramString, Map paramMap, List paramList)
    throws JRException
  {
    this.request.setAttribute(JasperPrintServlet.PRINT_PREVIEW_FLAG, "1");
    paramMap.put(JasperPrintServlet.PRINTBACKGROUND, Boolean.TRUE);
    addPrintData(paramString, paramMap, paramList);
  }

  public void addPrintBackgroundData(String paramString, Map paramMap, List paramList)
    throws JRException
  {
    paramMap.put(JasperPrintServlet.PRINTBACKGROUND, Boolean.TRUE);
    addPrintData(paramString, paramMap, paramList);
  }

  public final String taCommonExportExcl()
    throws Exception
  {
    String str = this.request.getParameter("_grid_item_export_excel");
    JSONArray localJSONArray = (JSONArray)JSonFactory.json2bean(str.toString(), JSONArray.class);
    HSSFWorkbook localHSSFWorkbook = new HSSFWorkbook();
    HSSFSheet localHSSFSheet = localHSSFWorkbook.createSheet("sheet1");
    for (int i = 0; i < localJSONArray.size(); i++)
    {
      HSSFRow localObject1 = localHSSFSheet.createRow(i);
      Object localObject2;
      int j;
      HSSFCell localHSSFCell;
      HSSFRichTextString localHSSFRichTextString;
      if (1176801 < str.length())
      {
        localObject2 = (ArrayList)localJSONArray.get(i);
        for (j = 0; j < ((ArrayList)localObject2).size(); j++)
        {
          localHSSFCell = ((HSSFRow)localObject1).createCell(j);
          localHSSFRichTextString = new HSSFRichTextString(((ArrayList)localObject2).get(j).toString());
          localHSSFCell.setCellValue(localHSSFRichTextString);
        }
      }
      else
      {
        localObject2 = (JSONArray)localJSONArray.get(i);
        for (j = 0; j < ((JSONArray)localObject2).size(); j++)
        {
          localHSSFCell = ((HSSFRow)localObject1).createCell(j);
          localHSSFRichTextString = new HSSFRichTextString(((JSONArray)localObject2).get(j).toString());
          localHSSFCell.setCellValue(localHSSFRichTextString);
        }
      }
    }
    HttpServletResponse localHttpServletResponse = ServletActionContext.getResponse();
    localHttpServletResponse.setContentType("application/octet-stream");
    localHttpServletResponse.addHeader("Content-Disposition", "attachment;filename=export.xls");
    Object localObject1 = localHttpServletResponse.getOutputStream();
    localHSSFWorkbook.write((OutputStream)localObject1);
    ((ServletOutputStream)localObject1).flush();
    ((ServletOutputStream)localObject1).close();
    return (String)(String)null;
  }

  public final String taResetCurrentPage()
    throws Exception
  {
    String str = UserSession.getUserSession(this.request).getCurrentBusiness().getMenuUrl();
    if (StringUtil.isNotEmpty(str))
    {
      HttpServletResponse localHttpServletResponse = ServletActionContext.getResponse();
      localHttpServletResponse.sendRedirect(str);
      return null;
    }
    return null;
  }

  public ParamDTO reBuildDto(String paramString, ParamDTO paramParamDTO)
  {
    ParamDTO localParamDTO = new ParamDTO();
    Iterator localIterator = paramParamDTO.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      int i = str1.indexOf(paramString) + paramString.length();
      String str2 = str1.substring(i);
      localParamDTO.put(str2, paramParamDTO.get(str1));
    }
    localParamDTO.setGridInfo(paramParamDTO.getGridInfo());
    localParamDTO.setUserInfo(paramParamDTO.getUserInfo());
    return localParamDTO;
  }

  public Map reBuildMap(String paramString, DTO paramDTO)
  {
    HashMap localHashMap = new HashMap();
    Iterator localIterator = paramDTO.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localHashMap.put(paramString + str, paramDTO.get(str));
    }
    return localHashMap;
  }

  protected ResultBean setSelectInputList(String paramString, List<?> paramList)
  {
    String str1 = JSonFactory.bean2json(paramList);
    if ((paramString != null) && (!"".equals(paramString)) && (str1 != null) && (!"".equals(str1)))
    {
      HashMap localHashMap = (HashMap)UserSession.getUserSession(this.request).getCurrentBusiness().getSessionResource("__selectinput_flag_map_");
      if ((localHashMap != null) && (localHashMap.get(paramString) != null))
      {
        String str2 = getCodeDesc((String)localHashMap.get(paramString), String.valueOf(str1), getDto().getUserInfo().getOrgId());
        if ((str2 != null) && (!"".equals(str2)))
          this.resultBean.addData(paramString + "_desc", str2);
      }
    }
    return this.resultBean.addData(paramString, str1);
  }
}
