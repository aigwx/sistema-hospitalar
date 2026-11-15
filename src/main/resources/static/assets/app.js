"use strict";

// Os nomes dos campos seguem os DTOs utilizados no backend Spring Boot.
const F = (name, label, type = "text", required = false) => ({ name, label, type, required });
const modules = [
  {key:"pacientes",title:"Pacientes",icon:"♙",desc:"Cadastro e informações dos pacientes.",remove:true,fields:[F("nome","Nome completo","text",true),F("cpf","CPF (11 números)","text",true),F("dataNascimento","Data de nascimento","date",true),F("logradouro","Logradouro"),F("numero","Número"),F("cidade","Cidade"),F("numeroCarteirinha","Número da carteirinha")]},
  {key:"profissionais",title:"Profissionais",icon:"⚕",desc:"Profissionais vinculados ao hospital.",remove:true,fields:[F("nome","Nome completo","text",true),F("cpf","CPF (11 números)","text",true),F("dataAdmissao","Data de admissão","date",true)]},
  {key:"medicos",title:"Médicos",icon:"✚",desc:"Registro de médicos e respectivos CRMs.",remove:true,fields:[F("idProfissional","ID do profissional","number",true),F("crm","CRM","text",true)]},
  {key:"enfermeiros",title:"Enfermeiros",icon:"✚",desc:"Registro de enfermeiros e respectivos CORENs.",remove:true,fields:[F("idProfissional","ID do profissional","number",true),F("coren","COREN","text",true)]},
  {key:"especialidades",title:"Especialidades",icon:"✧",desc:"Especialidades médicas cadastradas.",remove:true,fields:[F("nome","Nome da especialidade","text",true),F("descricao","Descrição","textarea")]},
  {key:"agendamentos",title:"Agendamentos",icon:"▦",desc:"Consultas e agendamentos hospitalares.",audit:true,fields:[F("idPaciente","ID do paciente","number",true),F("idProfissional","ID do profissional","number",true),F("dataHora","Data e hora","datetime-local",true),F("status","Status","text",true),F("motivo","Motivo","textarea")]},
  {key:"atendimentos",title:"Atendimentos",icon:"▤",desc:"Atendimentos realizados e dados clínicos.",audit:true,fields:[F("idAgendamento","ID do agendamento","number",true),F("dataHora","Data e hora","datetime-local",true),F("diagnostico","Diagnóstico","textarea"),F("observacoes","Observações","textarea")]},
  {key:"prontuarios",title:"Prontuários",icon:"▣",desc:"Prontuários e observações gerais dos pacientes.",fields:[F("idPaciente","ID do paciente","number",true),F("observacoesGerais","Observações gerais","textarea")]},
  {key:"telefones",title:"Telefones",icon:"☎",desc:"Telefones de contato dos pacientes.",remove:true,fields:[F("idPaciente","ID do paciente","number",true),F("telefone","Telefone","text",true)]},
  {key:"medicamentos",title:"Medicamentos",icon:"✚",desc:"Cadastro de medicamentos e fabricantes.",remove:true,fields:[F("nome","Nome","text",true),F("principioAtivo","Princípio ativo","text",true),F("fabricante","Fabricante")]},
  {key:"prescricoes",title:"Prescrições",icon:"▤",desc:"Prescrições vinculadas aos atendimentos.",remove:true,fields:[F("idAtendimento","ID do atendimento","number",true),F("observacoes","Observações","textarea")]},
  {key:"itens-prescricao",title:"Itens da prescrição",icon:"☷",desc:"Medicamentos e dosagens prescritos.",remove:true,composite:true,fields:[F("idPrescricao","ID da prescrição","number",true),F("numeroItem","Número do item","number",true),F("idMedicamento","ID do medicamento","number",true),F("dosagem","Dosagem","text",true),F("frequencia","Frequência","text",true),F("duracao","Duração","text",true)]},
  {key:"exames",title:"Exames",icon:"⌕",desc:"Solicitações, resultados e status de exames.",remove:true,fields:[F("idAtendimento","ID do atendimento","number",true),F("tipo","Tipo de exame","text",true),F("dataResultado","Data do resultado","datetime-local"),F("resultado","Resultado","textarea"),F("status","Status","text",true)]},
  {key:"usuarios",title:"Usuários",icon:"♙",desc:"Usuários que acessam o sistema.",remove:true,fields:[F("nome","Nome completo","text",true),F("email","E-mail","email",true),F("senha","Senha","password",true),F("ativo","Usuário ativo","checkbox"),F("idsGrupos","IDs dos grupos (separados por vírgula)")]},
  {key:"grupos",title:"Grupos de usuários",icon:"♧",desc:"Grupos utilizados na organização dos acessos.",remove:true,fields:[F("nome","Nome do grupo","text",true),F("descricao","Descrição","textarea")]}
,  {key:"relatorio-agendamentos",title:"Relatório de agendamentos",icon:"▥",desc:"Dados da VIEW vw_agendamentos no MySQL, exibidos por meio da API.",endpoint:"/relatorios/agendamentos",readOnly:true,fields:[F("idAgendamento","ID do agendamento"),F("paciente","Paciente"),F("profissional","Profissional"),F("dataHora","Data e hora"),F("status","Status"),F("motivo","Motivo")]}
];

const $ = id => document.getElementById(id);
let current = null;
let currentRows = [];
let editing = null;
let toastTimeout;
const escapeHTML = value => String(value ?? "").replace(/[&<>"']/g, c => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));

async function api(path, options={}) {
  const response = await fetch(path, options);
  const text = await response.text();
  let result;
  try {result = text ? JSON.parse(text) : null;} catch {result=text;}
  if (!response.ok) {
    const error = typeof result === "string" ? result : (result?.message || result?.detail || result?.error || JSON.stringify(result));
    throw new Error(`HTTP ${response.status}: ${error || response.statusText}`);
  }
  return result;
}
function notify(message) {
  const toast = $("toast"); toast.textContent=message; toast.classList.add("visible");
  clearTimeout(toastTimeout); toastTimeout=setTimeout(()=>toast.classList.remove("visible"),4000);
}
function recordId(row) {
  if (current.composite) return `${row.idPrescricao}/${row.numeroItem}`;
  const key = Object.keys(row).find(k=>/^id[A-Z]/.test(k));
  return key ? row[key] : null;
}
function detailPath(id) {return `/${current.key}/${current.composite ? String(id) : encodeURIComponent(id)}`;}
function fieldLabel(key) {return current.fields.find(f=>f.name===key)?.label || key.replace(/([A-Z])/g," $1");}
function nice(value) {
  if (value === null || value === undefined || value === "") return "—";
  if (typeof value === "boolean") return value ? "Sim" : "Não";
  if (Array.isArray(value)) return value.join(", ");
  if (typeof value === "object") return JSON.stringify(value);
  return String(value);
}
function setNavigation() {
  $("navigation").innerHTML = `<button class="nav-link active" data-home><span class="nav-icon">⌂</span>Visão geral</button>`+
    modules.map(m=>`<button class="nav-link" data-key="${m.key}"><span class="nav-icon">${m.icon}</span>${m.title}</button>`).join("");
  $("navigation").querySelector("[data-home]").addEventListener("click",showHome);
  $("navigation").querySelectorAll("[data-key]").forEach(button=>button.addEventListener("click",()=>showModule(button.dataset.key)));
}
function selectNav(key) {
  $("navigation").querySelectorAll(".nav-link").forEach(button=>button.classList.toggle("active",key?button.dataset.key===key:button.hasAttribute("data-home")));
  $("sidebar").classList.remove("open");
}
function showHome() {
  current=null; $("homePage").classList.remove("hidden"); $("listPage").classList.add("hidden");
  $("breadcrumb").textContent="Visão geral";selectNav(null);refreshDashboard();
}
function showModule(key) {
  current=modules.find(m=>m.key===key); if(!current)return;
  $("homePage").classList.add("hidden"); $("listPage").classList.remove("hidden");
  $("newBtn").classList.toggle("hidden",!!current.readOnly);
  $("breadcrumb").textContent=current.title; $("sectionTitle").textContent=current.title;
  $("sectionDescription").textContent=current.desc; $("search").value="";
  $("tableNote").textContent=current.readOnly?"Somente leitura: dados consultados diretamente da VIEW no MySQL.":current.audit?"Auditoria: ID do usuário indicado no topo.":(!current.remove?"Registros preservados para manter o histórico.":"Use as ações para editar ou excluir.");
  selectNav(key);loadRecords();
}
async function loadRecords() {
  if(!current)return;
  const key = current.key;
  $("tableBody").innerHTML=`<tr><td class="empty">Carregando registros...</td></tr>`;
  try {
    const response=await api(current.endpoint || `/${key}`);
    if (current?.key !== key) return;
    currentRows=Array.isArray(response)?response:(response?.content||[]);
    drawTable();
  } catch(error) {
    if(current?.key!==key)return;
    currentRows=[]; drawTable(error.message);notify("Não foi possível carregar os registros.");
  }
}
function drawTable(error=null) {
  if(!current)return;
  const idFields=Object.keys(currentRows[0]||{}).filter(k=>/^id[A-Z]/.test(k));
  const columns=[...idFields.slice(0,current.composite?2:1),...current.fields.map(f=>f.name)].filter((x,i,arr)=>arr.indexOf(x)===i).slice(0,6);
  $("tableHead").innerHTML=`<tr>${columns.map(k=>`<th>${escapeHTML(fieldLabel(k))}</th>`).join("")}${current.readOnly?"":"<th>Ações</th>"}</tr>`;
  const search=$("search").value.trim().toLocaleLowerCase("pt-BR");
  const visible=currentRows.map((record,index)=>({record,index})).filter(({record})=>JSON.stringify(record).toLocaleLowerCase("pt-BR").includes(search));
  if(error || !visible.length) {
    $("tableBody").innerHTML=`<tr><td class="empty" colspan="${columns.length+(current.readOnly?0:1)}">${escapeHTML(error||(search?"Nenhum resultado para essa busca.":"Nenhum registro encontrado."))}</td></tr>`;
  } else {
    $("tableBody").innerHTML=visible.map(({record,index})=>{
      const id=recordId(record);
      return `<tr>${columns.map(key=>`<td title="${escapeHTML(nice(record[key]))}">${escapeHTML(nice(record[key]))}</td>`).join("")}${current.readOnly?"":`<td><div class="actions"><button class="action-btn" data-edit="${index}">Editar</button>${current.remove?`<button class="action-btn danger" data-remove="${index}">Excluir</button>`:""}</div></td>`}</tr>`;
    }).join("");
    $("tableBody").querySelectorAll("[data-edit]").forEach(b=>b.addEventListener("click",()=>openForm(currentRows[Number(b.dataset.edit)])));
    $("tableBody").querySelectorAll("[data-remove]").forEach(b=>b.addEventListener("click",()=>removeRecord(currentRows[Number(b.dataset.remove)])));
  }
  $("recordTotal").textContent=`${visible.length} de ${currentRows.length} registro(s)`;
}
function openForm(record=null) {
  editing=record;$("formTitle").textContent=record?`Editar ${current.title.toLowerCase()}`:`Cadastrar em ${current.title.toLowerCase()}`;
  $("formMessage").classList.add("hidden");
  $("formFields").innerHTML=current.fields.map(field=>{
    let value=record?.[field.name]??"";
    if(field.name==="idsGrupos"&&Array.isArray(value)) value=value.join(",");
    if(field.type==="checkbox")return `<div class="form-field"><label><input type="checkbox" id="field-${field.name}" ${value===false?"":"checked"}> ${escapeHTML(field.label)}</label></div>`;
    const isPasswordEdit=!!record && field.name==="senha";
    const required=field.required&&!isPasswordEdit?"required":"";
    let control=field.type==="textarea"?`<textarea id="field-${field.name}" ${required}>${escapeHTML(value)}</textarea>`:`<input id="field-${field.name}" type="${field.type}" value="${field.type==="password"?"":escapeHTML(value)}" ${required} ${field.type==="number"?'min="1" step="1"':""}>`;
    const hint=field.name==="idsGrupos"?"<small>Exemplo: 2,3</small>":isPasswordEdit?"<small>Deixe vazio para não enviar uma nova senha.</small>":"";
    const locked=!!record&&((current.key==="medicos"||current.key==="enfermeiros")&&field.name==="idProfissional"||(current.composite&&(field.name==="idPrescricao"||field.name==="numeroItem")));
    if(locked)control=control.replace(" id=", " disabled id=");
    return `<div class="form-field ${field.type==="textarea"?"full":""}"><label for="field-${field.name}">${escapeHTML(field.label)}${required?" *":""}</label>${control}${hint}</div>`;
  }).join("");
  $("modalOverlay").classList.remove("hidden");
}
function closeForm(){editing=null;$("modalOverlay").classList.add("hidden");}
function makePayload() {
  const payload={};
  for(const field of current.fields) {
    const input=$("field-"+field.name);
    if(field.type==="checkbox") {payload[field.name]=input.checked;continue;}
    const value=input.value.trim();
    if(field.name==="idsGrupos")payload.idsGrupos=value?value.split(",").map(v=>Number(v.trim())):[];
    else if(field.type==="number")payload[field.name]=value?Number(value):null;
    else if(field.type==="password"&&editing&&!value)continue;
    else payload[field.name]=value||null;
  }
  if(editing) {
    for(const key of Object.keys(editing))if(/^id[A-Z]/.test(key)&&!(key in payload))payload[key]=editing[key];
  }
  return payload;
}
async function saveRecord(event){
  event.preventDefault();
  const button=$("saveBtn");button.disabled=true;
  try {
    const path=editing?detailPath(recordId(editing)):`/${current.key}`;
    const headers={"Content-Type":"application/json; charset=utf-8"};
    if(current.audit)headers["X-Usuario-Id"]=$("userId").value.trim()||"4";
    await api(path,{method:editing?"PUT":"POST",headers,body:JSON.stringify(makePayload())});
    closeForm();notify("Registro salvo com sucesso.");await loadRecords();
  }catch(error){$("formMessage").textContent=error.message;$("formMessage").classList.remove("hidden");}
  finally{button.disabled=false;}
}
async function removeRecord(record){
  const id=recordId(record);
  if(!window.confirm(`Deseja realmente excluir o registro ${id}?`))return;
  try{await api(detailPath(id),{method:"DELETE"});notify(current.key==="usuarios"?"Usuário desativado com sucesso.":"Registro excluído com sucesso.");await loadRecords();}
  catch(error){notify(`Erro ao excluir: ${error.message}`);}
}
async function checkStatus(){
  try{await api("/pacientes");$("statusIndicator").className="status-indicator ok";$("statusText").textContent="API conectada";}
  catch{$("statusIndicator").className="status-indicator fail";$("statusText").textContent="API indisponível — inicie o backend";}
}
async function refreshDashboard(){
  const targets=[{key:"pacientes",name:"Pacientes",icon:"♙"},{key:"profissionais",name:"Profissionais",icon:"⚕"},{key:"agendamentos",name:"Agendamentos",icon:"▦"},{key:"atendimentos",name:"Atendimentos",icon:"▤"}];
  $("metrics").innerHTML=targets.map(t=>`<div class="metric"><div class="metric-header">${t.name}<span class="metric-icon">${t.icon}</span></div><div class="metric-number" id="count-${t.key}">—</div><div class="metric-sub">Registros cadastrados</div></div>`).join("");
  for(const t of targets){try{const r=await api("/"+t.key);const el=$("count-"+t.key);if(el)el.textContent=Array.isArray(r)?r.length:(r?.totalElements??"—");}catch{const el=$("count-"+t.key);if(el)el.textContent="—";}}
  checkStatus();
}
function initialize(){
  setNavigation();
  $("dateChip").textContent=new Intl.DateTimeFormat("pt-BR",{dateStyle:"long"}).format(new Date());
  $("shortcuts").innerHTML=[...modules.slice(0,7),modules[modules.length-1]].map(m=>`<button class="shortcut" data-shortcut="${m.key}"><span>${m.icon}</span><b>${m.title}</b></button>`).join("");
  $("shortcuts").querySelectorAll("[data-shortcut]").forEach(b=>b.addEventListener("click",()=>showModule(b.dataset.shortcut)));
  $("newBtn").addEventListener("click",()=>openForm());
  $("refreshBtn").addEventListener("click",loadRecords);
  $("search").addEventListener("input",()=>drawTable());
  $("dataForm").addEventListener("submit",saveRecord);
  $("closeBtn").addEventListener("click",closeForm);
  $("cancelBtn").addEventListener("click",closeForm);
  $("modalOverlay").addEventListener("click",event=>{if(event.target===$("modalOverlay"))closeForm();});
  $("retryConnection").addEventListener("click",checkStatus);
  $("bannerPatients").addEventListener("click",()=>showModule("pacientes"));
  $("menuToggle").addEventListener("click",()=>$("sidebar").classList.toggle("open"));
  window.addEventListener("keydown",event=>{if(event.key==="Escape"&&!$("modalOverlay").classList.contains("hidden"))closeForm();});
  showHome();
}
initialize();
