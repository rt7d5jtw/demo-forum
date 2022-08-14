/** 
 * Notification example with fade effect.
 * @param notifyString string
 * @returns HTMLElement
 */
const notify = (notifyString) => {
  const notifyDiv = document.createElement("div");
  const content = document.createTextNode(notifyString);

  notifyDiv.appendChild(content);

  setTimeout(() => { 
    notifyDiv.style.background = "rgba(26, 179, 148, 0.45)";
    notifyDiv.style.color = "white";
  }, 250) // Comes up with 250ms delay

  notifyDiv.classList.add("notification");
  notifyDiv.id = "notifier";

  // Change display to flex from none.
  notifyDiv.style.display = "flex";

  // Add notification to <main/>.
  const main = document.getElementById("main");
  document.body.insertBefore(notifyDiv, main);

  // Cleans up the notification div and its children
  const cleanup = () => {
    const div = document.getElementById("notifier");
    // Set to fade out by using transparent
    div.style.background = "transparent";
    div.style.color = "transparent";

    setTimeout(() => {
      // Remove children
      while (div.firstChild) {
        div.removeChild(div.firstChild);
      }
      div.remove();
    }, 5 * 1000); // 5 seconds to fade
  }

  // Run clean up function with delay
  setTimeout(
    cleanup
    , 5 * 1000); // 5 seconds after click
}
