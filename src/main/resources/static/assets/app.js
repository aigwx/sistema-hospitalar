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
let sessao = null;
let modulosVisiveis = [];
const escapeHTML = value => String(value ?? "").replace(/[&<>"']/g, c => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));

// Recupera o token CSRF da sessão autenticada.
async function obterCsrf() {
  const resposta = await fetch("/csrf", {
    method: "GET",
    credentials: "same-origin",
    cache: "no-store",
    headers: { "Accept": "application/json" }
  });

  // Se a sessão expirou, o Spring Security redireciona para /login.
  if (resposta.redirected && new URL(resposta.url).pathname === "/login") {
    window.location.assign("/login");
    throw new Error("Sessão expirada. Entre novamente.");
  }
  if (!resposta.ok) {
    throw new Error(`Falha ao obter CSRF: HTTP ${resposta.status}`);
  }
  const tipo = resposta.headers.get("Content-Type") || "";
  if (!tipo.includes("application/json")) {
    throw new Error("O endpoint /csrf não retornou JSON. Confira sua sessão.");
  }
  return resposta.json();
}

// Usa a sessão do navegador e acrescenta CSRF a POST, PUT, PATCH e DELETE.
async function api(path, options={}) {
  const metodo = (options.method || "GET").toUpperCase();
  const headers = new Headers(options.headers || {});
  if (["POST", "PUT", "PATCH", "DELETE"].includes(metodo)) {
    const csrf = await obterCsrf();
    headers.set(csrf.headerName, csrf.token);
  }
  const response = await fetch(path, {
    ...options,
    method: metodo,
    headers,
    credentials: "same-origin"
  });
  if (response.redirected && new URL(response.url).pathname === "/login") {
    window.location.assign("/login");
    throw new Error("Sessão expirada. Entre novamente.");
  }
  const text = await response.text();
  let result;
  try { result = text ? JSON.parse(text) : null; } catch { result = text; }
  if (!response.ok) {
    const error = typeof result === "string" ? result :
      (result?.message || result?.detail || result?.error || JSON.stringify(result));
    throw new Error(`HTTP ${response.status}: ${error || response.statusText}`);
  }
  return result;
}

// Faz o logout através de um formulário POST nativo.
// O navegador acompanha o redirecionamento do Spring Security automaticamente.
async function sairDoSistema() {
  const botao = document.getElementById("botaoSair");
  botao.disabled = true;
  try {
    const csrf = await obterCsrf();
    const form = document.createElement("form");
    form.method = "POST";
    form.action = "/logout";
    form.style.display = "none";

    const token = document.createElement("input");
    token.type = "hidden";
    token.name = csrf.parameterName;
    token.value = csrf.token;
    form.appendChild(token);

    document.body.appendChild(form);
    form.submit();
  } catch (erro) {
    console.error("Erro no logout:", erro);
    notify(erro.message || "Não foi possível sair do sistema.");
    botao.disabled = false;
  }
}

function configurarLogout() {
  const topbar = document.querySelector(".topbar");
  if (!topbar || document.getElementById("botaoSair")) return;
  const botao = document.createElement("button");
  botao.id = "botaoSair";
  botao.type = "button";
  botao.textContent = "Sair";
  botao.className = "btn secondary";
  botao.style.marginLeft = "12px";
  botao.addEventListener("click", sairDoSistema);
  topbar.appendChild(botao);
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
// A visibilidade do menu acompanha as mesmas regras do Spring Security.
const permissao = {
  ADMINISTRADOR: { todos: true },
  MEDICO: { ver: ["pacientes","telefones","profissionais","medicos","especialidades","agendamentos","atendimentos","prontuarios","exames","prescricoes","itens-prescricao","medicamentos","relatorio-agendamentos"], escrever: ["atendimentos","prontuarios","exames","prescricoes","itens-prescricao"] },
  ENFERMEIRO: { ver: ["pacientes","telefones","profissionais","medicos","especialidades","agendamentos","atendimentos","prontuarios","exames","prescricoes","itens-prescricao","medicamentos","relatorio-agendamentos"], escrever: [] },
  RECEPCAO: { ver: ["pacientes","telefones","profissionais","medicos","especialidades","agendamentos","relatorio-agendamentos"], escrever: ["pacientes","telefones","agendamentos"] }
};
function perfilAtual() {
  const perfis = sessao?.perfis || [];
  for (const role of ["ADMINISTRADOR","MEDICO","ENFERMEIRO","RECEPCAO"]) {
    if (perfis.includes("ROLE_" + role)) return role;
  }
  return null;
}
function temAcesso(modulo, operacao="ver") {
  const cfg = permissao[perfilAtual()];
  if (!cfg) return false;
  if (cfg.todos) return true;
  return (cfg[operacao] || []).includes(modulo.key);
}
async function carregarSessao() {
  try {
    sessao = await api("/auth/me");
    if (!perfilAtual()) throw new Error("Perfil de acesso não encontrado");
    modulosVisiveis = modules.filter(m=>temAcesso(m));
    const antigo = document.querySelector(".user-setting");
    if (antigo) antigo.remove();
    const topbar = document.querySelector(".topbar");
    const info = document.createElement("div");
    info.className = "user-profile";
    info.innerHTML = `<span class="profile-email">${escapeHTML(sessao.email)}</span><span class="profile-role">${escapeHTML(({ADMINISTRADOR:"Administrador",MEDICO:"Médico",ENFERMEIRO:"Enfermeiro",RECEPCAO:"Recepção"})[perfilAtual()])}</span>`;
    const sair = document.getElementById("botaoSair");
    topbar.insertBefore(info, sair);
  } catch (e) {
    document.querySelector(".user-setting")?.remove();
    if (e.message && !e.message.includes("Sessão expirada")) notify("Falha ao identificar o usuário: " + e.message);
    throw e;
  }
}
function setNavigation() {
  const groups = [
    ["ATENDIMENTO", ["pacientes","agendamentos","atendimentos","prontuarios","telefones"]],
    ["EQUIPE E CUIDADO", ["profissionais","medicos","enfermeiros","especialidades","exames","medicamentos","prescricoes","itens-prescricao"]],
    ["GESTÃO", ["relatorio-agendamentos","usuarios","grupos"]]
  ];
  $("navigation").innerHTML = `<button class="nav-link active" data-home><span class="nav-icon">⌂</span>Visão geral</button>` +
    groups.map(([label, keys]) => {
      const items = modulosVisiveis.filter(m => keys.includes(m.key));
      return items.length ? `<div class="nav-group">${label}</div>` + items.map(m=>`<button class="nav-link" data-key="${m.key}"><span class="nav-icon">${m.icon}</span>${m.title}</button>`).join("") : "";
    }).join("");
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
  current=modulosVisiveis.find(m=>m.key===key); if(!current){notify("Acesso não permitido.");return;}
  $("homePage").classList.add("hidden"); $("listPage").classList.remove("hidden");
  $("newBtn").classList.toggle("hidden",!!current.readOnly || !temAcesso(current,"escrever"));
  $("breadcrumb").textContent=current.title; $("sectionTitle").textContent=current.title;
  $("sectionDescription").textContent=current.desc; $("search").value="";
  $("tableNote").textContent=(current.readOnly || !temAcesso(current,"escrever"))?"Consulta disponível para seu perfil.":current.audit?"Alterações vinculadas ao seu usuário.":(!current.remove?"Registros preservados para manter o histórico.":"Use as ações para editar ou excluir.");
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
  $("tableHead").innerHTML=`<tr>${columns.map(k=>`<th>${escapeHTML(fieldLabel(k))}</th>`).join("")}${(current.readOnly || !temAcesso(current,"escrever"))?"":"<th>Ações</th>"}</tr>`;
  const search=$("search").value.trim().toLocaleLowerCase("pt-BR");
  const visible=currentRows.map((record,index)=>({record,index})).filter(({record})=>JSON.stringify(record).toLocaleLowerCase("pt-BR").includes(search));
  if(error || !visible.length) {
    $("tableBody").innerHTML=`<tr><td class="empty" colspan="${columns.length+((current.readOnly || !temAcesso(current,"escrever"))?0:1)}">${escapeHTML(error||(search?"Nenhum resultado para essa busca.":"Nenhum registro encontrado."))}</td></tr>`;
  } else {
    $("tableBody").innerHTML=visible.map(({record,index})=>{
      const id=recordId(record);
      return `<tr>${columns.map(key=>`<td title="${escapeHTML(nice(record[key]))}">${escapeHTML(nice(record[key]))}</td>`).join("")}${(current.readOnly || !temAcesso(current,"escrever"))?"":`<td><div class="actions"><button class="action-btn" data-edit="${index}">Editar</button>${(current.remove && temAcesso(current,"excluir"))?`<button class="action-btn danger" data-remove="${index}">Excluir</button>`:""}</div></td>`}</tr>`;
    }).join("");
    $("tableBody").querySelectorAll("[data-edit]").forEach(b=>b.addEventListener("click",()=>openForm(currentRows[Number(b.dataset.edit)])));
    $("tableBody").querySelectorAll("[data-remove]").forEach(b=>b.addEventListener("click",()=>removeRecord(currentRows[Number(b.dataset.remove)])));
  }
  $("recordTotal").textContent=`${visible.length} de ${currentRows.length} registro(s)`;
}
function openForm(record=null) {
  if (!temAcesso(current,"escrever") || current.readOnly) { notify("Seu perfil não permite alterações neste módulo.");return; }
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
  if (!temAcesso(current,"escrever")) return notify("Operação não permitida.");
  const button=$("saveBtn");button.disabled=true;
  try {
    const path=editing?detailPath(recordId(editing)):`/${current.key}`;
    const headers={"Content-Type":"application/json; charset=utf-8"};
    if(current.audit)headers["X-Usuario-Id"]=String(sessao.idUsuario);
    await api(path,{method:editing?"PUT":"POST",headers,body:JSON.stringify(makePayload())});
    closeForm();notify("Registro salvo com sucesso.");await loadRecords();
  }catch(error){$("formMessage").textContent=error.message;$("formMessage").classList.remove("hidden");}
  finally{button.disabled=false;}
}
async function removeRecord(record){
  if (!temAcesso(current,"excluir")) return notify("Exclusão não permitida.");
  const id=recordId(record);
  if(!window.confirm(`Deseja realmente excluir o registro ${id}?`))return;
  try{await api(detailPath(id),{method:"DELETE"});notify(current.key==="usuarios"?"Usuário desativado com sucesso.":"Registro excluído com sucesso.");await loadRecords();}
  catch(error){notify(`Erro ao excluir: ${error.message}`);}
}
async function checkStatus(){
  try{await api("/auth/me");$("statusIndicator").className="status-indicator ok";$("statusText").textContent="Sistema conectado";}
  catch{$("statusIndicator").className="status-indicator fail";$("statusText").textContent="Conexão indisponível — tente novamente";}
}
async function refreshDashboard(){
  const targets=[{key:"pacientes",name:"Pacientes",icon:"♙"},{key:"profissionais",name:"Profissionais",icon:"⚕"},{key:"agendamentos",name:"Agendamentos",icon:"▦"},{key:"atendimentos",name:"Atendimentos",icon:"▤"}].filter(t=>modulosVisiveis.some(m=>m.key===t.key));
  $("metrics").innerHTML=targets.map(t=>`<div class="metric"><div class="metric-header">${t.name}<span class="metric-icon">${t.icon}</span></div><div class="metric-number" id="count-${t.key}">—</div><div class="metric-sub">Registros cadastrados</div></div>`).join("");
  for(const t of targets){try{const r=await api("/"+t.key);const el=$("count-"+t.key);if(el)el.textContent=Array.isArray(r)?r.length:(r?.totalElements??"—");}catch{const el=$("count-"+t.key);if(el)el.textContent="—";}}
  checkStatus();
}
async function initialize(){
  configurarLogout();
  try { await carregarSessao(); } catch { return; }
  setNavigation();
  $("dateChip").textContent=new Intl.DateTimeFormat("pt-BR",{dateStyle:"long"}).format(new Date());
  $("shortcuts").innerHTML=[...modulosVisiveis.slice(0,7),...modulosVisiveis.filter(m=>m.key==="relatorio-agendamentos")].map(m=>`<button class="shortcut" data-shortcut="${m.key}"><span>${m.icon}</span><b>${m.title}</b></button>`).join("");
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
  document.addEventListener("click", event => { if (!$("sidebar").contains(event.target) && !$("menuToggle").contains(event.target)) $("sidebar").classList.remove("open"); });
  window.addEventListener("keydown",event=>{if(event.key==="Escape") $("sidebar").classList.remove("open");if(event.key==="Escape"&&!$("modalOverlay").classList.contains("hidden"))closeForm();});
  showHome();
}
initialize();
