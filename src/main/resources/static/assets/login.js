"use strict";
const form = document.getElementById("loginForm");
const message = document.getElementById("loginMessage");
const submit = document.getElementById("loginSubmit");
const password = document.getElementById("password");
const params = new URLSearchParams(location.search);
function showMessage(text, success = false) {
  message.textContent = text;
  message.className = "login-message" + (success ? " success" : "");
}
if (params.has("error")) showMessage("Não foi possível entrar. Confira seu e-mail e senha ou consulte o administrador sobre seu acesso.");
else if (params.has("logout")) showMessage("Você saiu do sistema com segurança.", true);
document.getElementById("togglePassword").addEventListener("click", event => {
  const visible = password.type === "password";
  password.type = visible ? "text" : "password";
  event.currentTarget.textContent = visible ? "Ocultar" : "Mostrar";
  event.currentTarget.setAttribute("aria-pressed", String(visible));
});
for (const type of ["keydown", "keyup"]) password.addEventListener(type, event => {
  document.getElementById("capsWarning").classList.toggle("hidden", !event.getModifierState("CapsLock"));
});
form.addEventListener("submit", async event => {
  event.preventDefault();
  submit.disabled = true;
  submit.textContent = "Entrando…";
  try {
    const response = await fetch("/csrf", {credentials:"same-origin", cache:"no-store", headers:{Accept:"application/json"}});
    if (!response.ok || !(response.headers.get("Content-Type") || "").includes("application/json")) throw new Error("Falha ao preparar a sessão.");
    const csrf = await response.json();
    if (!csrf.parameterName || !csrf.token) throw new Error("Token de sessão indisponível.");
    const token = document.createElement("input");
    token.type = "hidden"; token.name = csrf.parameterName; token.value = csrf.token;
    form.appendChild(token);
    HTMLFormElement.prototype.submit.call(form);
  } catch (error) {
    showMessage("Não foi possível conectar ao sistema. Verifique a conexão e tente novamente.");
    submit.disabled = false;
    submit.textContent = "Tentar novamente →";
  }
});
